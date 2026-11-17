# InspectionKit

## Concept
An IntelliJ plugin that adds focused inspections to help developers catch issues the IDE’s existing checks may not cover. It can grow over time with more checks; each check should integrate with IntelliJ’s standard inspection experience.

## Development

The project uses Kotlin, Gradle, and the IntelliJ Platform Gradle Plugin.

```sh
./gradlew runIde       # launch a sandbox IntelliJ IDEA instance
./gradlew buildPlugin  # build the plugin distribution
./gradlew verifyPlugin # verify plugin compatibility
```