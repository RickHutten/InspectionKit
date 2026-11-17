# LMB-001 — Report unused @Slf4j annotations

**Status:** Draft

## Story

As a developer, I want InspectionKit to identify `@Slf4j` annotations whose generated `log` field is not used so that I can remove unnecessary logging setup from a class.

## Scope

In scope:

- Inspect classes annotated with Lombok's `@Slf4j` annotation.
- Determine whether the generated `log` member has any resolved usages.
- Report the `@Slf4j` annotation when its generated `log` member is unused.
- Suggest removing the annotation without automatically modifying the source.

Out of scope:

- Lombok accessor annotations such as `@Getter` and `@Setter`.
- Other Lombok annotations that generate fields, methods, constructors, or other members.
- Automatically removing annotations or changing source code.
- Inferring usages that IntelliJ cannot resolve through its code model, such as reflective or framework-driven access.

## Acceptance criteria

- A class with `@Slf4j` and no resolved usages of its generated `log` member is reported.
- The inspection highlights the `@Slf4j` annotation itself rather than an invisible generated member.
- The reported result explains that the annotation can be removed, using wording such as **“Unused @Slf4j annotation; remove it.”**.
- A class with at least one resolved usage of its generated `log` member is not reported.
- The inspection does not claim to detect usages that IntelliJ cannot resolve through its code model.

## Dependencies

- **Blocked by:** [PLG-001 — Add an InspectionKit settings page scaffold](../PLG/PLG-001-settings-page-scaffold.md).
- **Related:** None.

## Notes

- The first implementation should use IntelliJ's supported inspection extension point and resolve the generated member through the IDE's Lombok-aware model where available.
- The supported source-language scope and Lombok plugin dependency assumptions must be confirmed during implementation planning.
- All shared inspection behavior follows the [inspection conventions](../README.md#inspection-conventions).
