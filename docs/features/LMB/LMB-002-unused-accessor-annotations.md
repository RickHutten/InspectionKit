# LMB-002 — Report unused @Getter and @Setter annotations

**Status:** Draft

## Story

As a developer, I want InspectionKit to identify field-level Lombok `@Getter` and `@Setter` annotations whose generated accessors are not used so that I can remove unnecessary accessor generation from a field.

## Scope

In scope:

- Inspect fields annotated with Lombok's `@Getter` or `@Setter` annotations.
- Determine whether each generated getter and setter has any resolved usages.
- Report an annotation when its corresponding generated accessor is unused.
- Highlight the unused annotation itself and suggest removing it without automatically modifying the source.
- Evaluate `@Getter` and `@Setter` independently when both are present on the same field.

Out of scope:

- Class-level `@Getter` and `@Setter` annotations.
- Other Lombok annotations that generate fields, methods, constructors, or other members.
- Automatically removing annotations or changing source code.
- Treating direct field access as a usage of the generated getter or setter.
- Inferring usages that IntelliJ cannot resolve through its code model, such as reflective or framework-driven access.

## Acceptance criteria

- A field with `@Getter` and no resolved usages of its generated getter is reported on the `@Getter` annotation.
- A field with `@Setter` and no resolved usages of its generated setter is reported on the `@Setter` annotation.
- The reported result explains that the unused annotation can be removed, using wording such as **“Unused @Getter annotation; remove it.”** or **“Unused @Setter annotation; remove it.”**.
- When both annotations are present, each annotation is evaluated independently: a used getter does not prevent an unused setter from being reported, and vice versa.
- A generated getter or setter with at least one resolved usage is not reported.
- The inspection does not claim to detect usages that IntelliJ cannot resolve through its code model.

## Dependencies

- **Blocked by:** [PLG-001 — Add an InspectionKit settings page scaffold](../PLG/PLG-001-settings-page-scaffold.md).
- **Related:** [LMB-001 — Report unused @Slf4j annotations](LMB-001-unused-slf4j.md).

## Notes

- The first implementation should use IntelliJ's supported inspection extension point and resolve generated accessors through the IDE's Lombok-aware model where available.
- The supported source-language scope and Lombok plugin dependency assumptions must be confirmed during implementation planning.
- All shared inspection behavior follows the [inspection conventions](../README.md#inspection-conventions).
