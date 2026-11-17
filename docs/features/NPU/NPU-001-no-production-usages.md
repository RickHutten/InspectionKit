# NPU-001 — Report functions with no production usages

**Status:** Draft

## Story

As a developer, I want InspectionKit to identify production functions that are used only by tests so that I can find code that is not exercised by production code.

## Scope

In scope:

- Inspect functions declared in production source roots.
- Report a function when it has at least one usage in test code and no usages in production code.
- Gray out the function and show a message such as **“No usages in production code.”**.
- Rely on IntelliJ's recognized production and test source roots to classify declarations and usages.

Out of scope:

- Functions declared in test source roots.
- Functions with no usages anywhere; those remain the responsibility of IntelliJ's existing unused-code inspections.
- Changes to or replacement of IntelliJ's existing unused-code inspections.
- Detection of usages discoverable only through reflection, framework configuration, or other dynamic mechanisms.
- Configuration options for enabling, disabling, or tuning this inspection.

## Acceptance criteria

- A production function referenced by test code but not by production code is reported with the message **“No usages in production code.”** or an approved equivalent.
- The reported function is visually grayed out in the editor.
- The result appears in IntelliJ's standard inspection results and provides the normal suppression options.
- A function declared in test code is not reported by this inspection.
- A function with no usages in either production or test code is not reported by this inspection.
- A production function with at least one production usage is not reported, even when it is also referenced by tests.
- The inspection does not claim to detect usages that IntelliJ cannot resolve through its production and test source-root model.

## Dependencies

- **Blocked by:** [PLG-001 — Add an InspectionKit settings page scaffold](../PLG/PLG-001-settings-page-scaffold.md).
- **Related:** None.

## Notes

- The first implementation should use IntelliJ's supported inspection extension point and standard problem registration APIs.
- The supported source-language scope must be confirmed during implementation planning; this story describes the behavior in terms of IntelliJ-recognized functions and source roots rather than assuming dynamic usage discovery.
- All shared inspection behavior follows the [inspection conventions](../README.md#inspection-conventions).
