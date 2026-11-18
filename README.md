# InspectionKit

## Concept
An IntelliJ plugin that adds focused inspections to help developers catch issues the IDE’s existing checks may not cover. It can grow over time with more checks; each check should integrate with IntelliJ’s standard inspection experience.

## Development

The project uses Kotlin 2.2.21, Gradle 9.5.0, and the IntelliJ Platform Gradle Plugin 2.20.1 against IntelliJ IDEA 2025.2.6.1. Run Gradle with JDK 21 for the pinned target platform (on macOS: `export JAVA_HOME=$(/usr/libexec/java_home -v 21)`).

```sh
./gradlew runIde       # launch a sandbox IntelliJ IDEA instance
./gradlew buildPlugin  # build the plugin distribution
./gradlew verifyPlugin # verify plugin compatibility
```