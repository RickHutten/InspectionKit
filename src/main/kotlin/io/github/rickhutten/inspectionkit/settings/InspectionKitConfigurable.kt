package io.github.rickhutten.inspectionkit.settings

import com.intellij.openapi.options.Configurable
import java.awt.BorderLayout
import java.util.ResourceBundle
import javax.swing.BorderFactory
import javax.swing.JComponent
import javax.swing.JLabel
import javax.swing.JPanel

class InspectionKitConfigurable : Configurable {
    private var panel: JPanel? = null

    override fun getDisplayName(): String = message("settings.inspectionkit.display.name")

    override fun createComponent(): JComponent {
        val settingsPanel = JPanel(BorderLayout(0, 8))
        settingsPanel.border = BorderFactory.createEmptyBorder(12, 12, 12, 12)
        settingsPanel.add(JLabel(displayName), BorderLayout.NORTH)
        settingsPanel.add(JLabel(message("settings.inspectionkit.description")), BorderLayout.CENTER)
        panel = settingsPanel
        return settingsPanel
    }

    override fun isModified(): Boolean = false

    override fun apply() = Unit

    override fun reset() = Unit

    override fun disposeUIResources() {
        panel = null
    }

    private fun message(key: String): String =
        ResourceBundle.getBundle("messages.InspectionKitBundle").getString(key)
}
