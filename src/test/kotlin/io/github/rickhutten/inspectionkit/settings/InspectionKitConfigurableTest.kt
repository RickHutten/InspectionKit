package io.github.rickhutten.inspectionkit.settings

import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.options.Configurable
import com.intellij.testFramework.fixtures.BasePlatformTestCase
import org.w3c.dom.Element
import java.awt.Component
import javax.swing.JLabel
import javax.swing.JPanel
import javax.xml.parsers.DocumentBuilderFactory

class InspectionKitConfigurableTest : BasePlatformTestCase() {
    fun testSettingsPageIsRegisteredUnderToolsAndIsStateless() {
        val descriptor = javaClass.getResourceAsStream("/META-INF/plugin.xml")
        assertNotNull("The plugin descriptor must be available to the test runtime", descriptor)

        descriptor!!.use { stream ->
            val document = DocumentBuilderFactory.newInstance()
                .newDocumentBuilder()
                .parse(stream)
            val configurable = document.getElementsByTagName("applicationConfigurable").item(0) as Element

            assertEquals("tools", configurable.getAttribute("parentId"))
            assertEquals("io.github.rickhutten.inspectionkit.settings", configurable.getAttribute("id"))
            assertEquals(
                InspectionKitConfigurable::class.java.name,
                configurable.getAttribute("instance"),
            )
            assertEquals("settings.inspectionkit.display.name", configurable.getAttribute("key"))
            assertEquals("messages.InspectionKitBundle", configurable.getAttribute("bundle"))
        }

        val page: Configurable = InspectionKitConfigurable()
        assertEquals("InspectionKit", page.displayName)

        val component = page.createComponent() ?: error("Settings page must create a component")
        val labels = collectLabels(component)
        assertTrue(labels.contains("InspectionKit"))
        assertTrue(labels.any { it.contains("focused inspections") })
        assertFalse(page.isModified)

        page.apply()
        page.reset()
        assertFalse(page.isModified)
        page.disposeUIResources()

        val reopenedComponent = page.createComponent() ?: error("Settings page must be reopenable")
        assertTrue(collectLabels(reopenedComponent).contains("InspectionKit"))
        assertFalse(page.isModified)
        page.disposeUIResources()
    }

    private fun collectLabels(component: Component): List<String> = when (component) {
        is JLabel -> listOf(component.text.orEmpty())
        is JPanel -> component.components.flatMap(::collectLabels)
        else -> emptyList()
    }
}
