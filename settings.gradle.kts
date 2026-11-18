import org.jetbrains.intellij.platform.gradle.extensions.intellijPlatform

rootProject.name = "InspectionKit"

pluginManagement {
    plugins {
        id("org.jetbrains.kotlin.jvm") version "2.2.21"
    }
}

plugins {
    id("org.jetbrains.intellij.platform.settings") version "2.20.1"
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()
        intellijPlatform {
            defaultRepositories()
        }
    }
}
