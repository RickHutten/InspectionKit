# Story conventions

## Inspection conventions

Every InspectionKit inspection follows the same IntelliJ integration behavior:

- It uses IntelliJ's supported inspection infrastructure and standard problem registration APIs.
- It appears in IntelliJ's normal inspection experience with standard severity and suppression options.
- It participates in commit-time code analysis when that analysis is enabled.
- Its user-facing text is localizable.

Individual inspection stories should define only behavior-specific scope and acceptance criteria; they inherit these conventions instead of repeating them.