# PLG-001 — Add an InspectionKit settings page scaffold

**Status:** Approved

## Story

As an IntelliJ IDEA user, I want an InspectionKit page in the IDE settings so that the plugin has a discoverable home for future configuration.

## Scope

In scope:

- Add an InspectionKit settings page under **Settings/Preferences → Tools**.
- Show the plugin name and a short introductory description on the page.
- Make the page available in the normal IntelliJ settings search and navigation.
- Provide a runnable plugin scaffold so the page can be opened in a sandbox IDE.

Out of scope:

- Configurable options or controls.
- Persisted settings.
- Inspection implementation or inspection-specific configuration.
- Changes to IntelliJ's existing inspection settings.

## Acceptance criteria

- The plugin builds and launches in a sandbox IntelliJ IDEA instance.
- An **InspectionKit** entry is visible under **Settings/Preferences → Tools**.
- Opening the entry displays the InspectionKit name and introductory description.
- The page can be opened, cancelled, and closed using normal IntelliJ settings behavior without errors.
- Saving the page does not create or modify any plugin settings because this scaffold has no configurable values yet.

## Dependencies

- **Blocked by:** None.
- **Related:** None.

## Notes

- This plugin has its own identity and must not reuse metadata or identifiers from another plugin.
- Keep user-facing text localizable.
- The implementation should use the supported IntelliJ settings extension point rather than internal settings APIs.
