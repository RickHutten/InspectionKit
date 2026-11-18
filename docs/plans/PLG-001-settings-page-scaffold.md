# PLG-001 — Implementation plan: settings page scaffold

**Story:** [PLG-001 — Add an InspectionKit settings page scaffold](../features/PLG/PLG-001-settings-page-scaffold.md)

**Plan status:** Implemented

## Approach

Bootstrap the currently empty repository as a small Kotlin IntelliJ Platform plugin, then register one application-level settings page beneath the built-in `Tools` settings group. The page will be a deliberately non-persistent `Configurable`: it will render the InspectionKit name and localized introductory text, but expose no editable values and therefore never become modified.

Use the supported `com.intellij.applicationConfigurable` extension point with `parentId="tools"`. Keep the implementation limited to plugin bootstrap, plugin metadata, the configurable UI, localization, and behavior-focused verification; do not add settings storage, inspection code, Lombok dependencies, or a shared configuration abstraction for future stories.

## Repository baseline

The repository currently has no Gradle build, Gradle wrapper, source tree, plugin descriptor, or test source. `README.md` establishes Kotlin, Gradle, and the IntelliJ Platform Gradle Plugin as the intended stack. The existing `.idea` metadata selects JDK 25, but it does not define a plugin target, plugin ID, package namespace, or compatibility range.

This means the first implementation step is project bootstrap rather than extending an existing plugin skeleton. The bootstrap must establish a unique InspectionKit plugin identity rather than copying metadata from another plugin.

## Implementation steps

1. **Establish the reproducible plugin build.**
   - Add `settings.gradle.kts`, `build.gradle.kts`, `gradle.properties`, a checked-in Gradle wrapper, and the wrapper support files.
   - Apply the current 2.x IntelliJ Platform Gradle Plugin and Kotlin JVM support using versions compatible with the selected IntelliJ IDEA target and available JDK/Gradle toolchain. Configure the Platform Settings plugin in `settings.gradle.kts` when repository management is declared there, and use the Platform plugin in the main build script.
   - Configure the IntelliJ IDEA platform dependency and the standard plugin tasks used by the repository README: `runIde`, `buildPlugin`, and `verifyPlugin`.
   - Keep the Settings search-index generation enabled; the page must remain discoverable through normal Settings search.
   - Pin the selected target platform and tool versions in version control; do not rely on an unrecorded local IDE installation.

2. **Define plugin metadata and extension registration.**
   - Add `src/main/resources/META-INF/plugin.xml` with the InspectionKit plugin ID, name, vendor, description, platform dependency, and the `com.intellij.applicationConfigurable` extension.
   - Set `parentId="tools"`, a stable configurable ID, and the configurable implementation class in the extension declaration. Use the descriptor's `key`/`bundle` attributes for the localized display name and add the plugin-level `resource-bundle` declaration.
   - Keep display text localizable through the plugin message bundle rather than hard-coding user-facing strings in the descriptor or Kotlin UI.
   - Use a normal Kotlin `class` with a no-argument constructor for the application configurable; do not register a Kotlin `object` as an extension.

3. **Implement the empty settings page.**
   - Add the configurable under the canonical plugin settings package, with a default constructor and the standard `Configurable` lifecycle.
   - Build a lightweight Swing/platform UI containing the InspectionKit name and a short introductory description. Use platform-supported components and layout utilities already available from the IDE platform; do not add a UI framework dependency for this page.
   - Return `false` from `isModified()` and make `apply()` and `reset()` no-ops because the page has no controls or persisted values.
   - Release the page component in `disposeUIResources()` and return the page's preferred focus component only if the chosen UI requires one.
   - Keep the UI state-free: do not introduce `PersistentStateComponent`, application services, serialization, or settings keys.

4. **Add localization resources.**
   - Add the message bundle under `src/main/resources/messages/` with keys for the settings display name and introductory description.
   - Ensure the descriptor and configurable use the same localized display name so settings navigation, search, and the page heading remain consistent.
   - Keep wording aligned with PLG-001 only; do not introduce configuration terminology or promise future options on the page.

5. **Add behavior-focused verification.**
   - Add an IntelliJ platform integration test that loads the plugin descriptor, finds the application configurable under the `Tools` parent, creates the component, verifies the localized name/description are present, confirms `isModified()` is false, exercises `apply()`, `reset()`, and disposal, and confirms no settings state is written.
   - Add a supported IDE UI/integration smoke test for the critical journey when the selected target exposes a stable test harness: open Settings/Preferences, find InspectionKit under Tools or via search, open the page, cancel/close it, reopen it, and save it without errors. Keep assertions on observable settings behavior rather than Swing implementation details.
   - If the target's supported test harness cannot automate the settings dialog reliably, retain the component integration test as the automated gate and document the sandbox journey as a required `runIde` smoke check rather than adding brittle internal-API UI tests.

6. **Update the development documentation only where needed.**
   - Confirm the README commands match the generated build and add any required local prerequisites, such as the supported JDK/Gradle range.
   - Do not add a second product specification to the README; keep page behavior and acceptance evidence in this plan/story.

## Affected paths

Expected paths after implementation:

- `settings.gradle.kts`, `build.gradle.kts`, `gradle.properties`
- `gradlew`, `gradlew.bat`, and `gradle/wrapper/`
- `src/main/resources/META-INF/plugin.xml`
- `src/main/kotlin/` for the settings configurable
- `src/main/resources/messages/` for localized text
- `src/test/` for the plugin/configurable integration test and, if supported, the settings UI smoke test
- `README.md` only for verified build prerequisites or command corrections

The exact plugin ID, package namespace, target IntelliJ platform version, and tool versions are not present in the repository and must be selected once during bootstrap, then used consistently across these paths.

## Technical decisions

- **Settings level:** application-level settings, because the page is a plugin home and has no project-specific data.
- **Settings parent:** the supported `Tools` group via `parentId="tools"`; do not use internal settings tree APIs.
- **Persistence:** none. A scaffold with no values must not create or modify plugin settings.
- **Localization:** message-bundle keys for the page title and description; no user-facing strings embedded only in Kotlin or XML attributes.
- **UI implementation:** a minimal platform/Swing panel with no new UI dependency and no premature settings model.
- **Compatibility:** use IntelliJ Platform Gradle Plugin 2.x, then pin an explicit target platform after checking the project’s supported JDK/Gradle combination. Compatibility with multiple IDE baselines is not part of PLG-001 unless separately approved.
- **Testing boundary:** verify the registered configurable and its lifecycle through the IntelliJ platform test boundary; use a supported UI test or sandbox smoke check for the actual Settings journey rather than testing private widget structure.

## Acceptance coverage

| Story criterion | Planned evidence |
|---|---|
| Plugin builds and launches in a sandbox | `buildPlugin`, `verifyPlugin`, and `runIde` against the pinned target platform |
| InspectionKit entry appears under Tools | Plugin descriptor registration test plus sandbox settings smoke check |
| Name and introductory description are displayed | Configurable integration test and sandbox smoke check |
| Open/cancel/close work without errors | Supported settings UI/integration smoke test; fallback documented if the target harness cannot automate the dialog |
| Saving creates or modifies no settings | Stateless configurable test asserting `isModified() == false` and no persistence component/state |

## Verification commands

Run from the repository root after implementation:

```sh
./gradlew verifyPluginProjectConfiguration
./gradlew buildSearchableOptions
./gradlew test
./gradlew buildPlugin
./gradlew verifyPlugin
./gradlew runIde
```

The `runIde` check must exercise the Settings/Preferences journey described above. If the project adds a dedicated supported UI test task, run that task as part of the affected verification lifecycle and document its exact command in the README.

## External references

- [JetBrains Settings Guide](https://plugins.jetbrains.com/docs/intellij/settings-guide.html)
- [JetBrains Settings Tutorial](https://plugins.jetbrains.com/docs/intellij/settings-tutorial.html)
- [IntelliJ Platform Gradle Plugin 2.x](https://plugins.jetbrains.com/docs/intellij/tools-intellij-platform-gradle-plugin.html)
- [JetBrains Plugin Configuration File](https://plugins.jetbrains.com/docs/intellij/plugin-configuration-file.html)
