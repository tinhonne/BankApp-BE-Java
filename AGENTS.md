# AGENTS.md
## 1. Project Overview
- Project name: Bank Project (`demo` Maven artifact) [NEEDS CONFIRMATION]
- Purpose: Spring Boot backend for managing bank customers and accounts, with requirements for transfers, transaction history, reporting, and CSV export.
- Current status: Partially implemented. Customer, account, user, and authentication code exists; transaction, reporting, approval, account-status management, and CSV export requirements are not fully implemented.
- Primary users: [NEEDS CONFIRMATION]

## 2. Technology Stack
- Language: Java 21
- Framework: Spring Boot 3.5.15
- Database: MySQL through Spring Data JPA and Hibernate
- Build tool: Maven with Maven Wrapper
- Testing: JUnit 5 and Spring Boot Test
- Main libraries: Spring Web, Jakarta Bean Validation, Spring Data JPA, Lombok, MapStruct 1.5.5.Final, Spring Security Crypto, Nimbus JOSE JWT 10.5, and MySQL Connector/J

## 3. Project Structure
- `src/main/java/com/example/demo/controller`: REST controllers for customer, account, user, and authentication endpoints.
- `src/main/java/com/example/demo/service`: Service interfaces defining application operations.
- `src/main/java/com/example/demo/service/impl`: Business logic and service implementations.
- `src/main/java/com/example/demo/repository`: Spring Data JPA repositories and database queries.
- `src/main/java/com/example/demo/entity`: JPA entities, shared entity fields, and domain enums.
- `src/main/java/com/example/demo/dto/request`: API request DTOs and input validation rules.
- `src/main/java/com/example/demo/dto/response`: API response DTOs, including common and paginated response wrappers.
- `src/main/java/com/example/demo/mapper`: MapStruct DTO/entity mappings.
- `src/main/java/com/example/demo/specification`: Dynamic JPA specifications for customer filtering.
- `src/main/java/com/example/demo/exception`: Application exceptions, error codes, and centralized exception handling.
- `src/main/resources/application.properties`: Spring application, datasource, and JPA configuration. Do not expose or duplicate sensitive values from this file.
- `src/test/java/com/example/demo`: Spring Boot and JUnit tests.
- `project-problem.md`: Functional requirements and business rules; compare it with current code before assuming a requirement is implemented.
- `repomix-output.xml`: Supporting repository snapshot only; current project files remain the source of truth.
- `target`: Generated Maven output. Do not edit or use it as source.
- `AGENTS_Rules.md`: Source template for this guidance.
Do not introduce a new folder structure without approval.

## 4. Architecture
- Architecture style: Layered Spring Boot REST application with controller, service, repository, persistence, mapping, and exception-handling layers.
- Request flow: HTTP request → controller → service interface/implementation → repository → MySQL; entities are converted to response DTOs before returning a common API response.
- Dependency direction: Controllers depend on service interfaces; service implementations depend on repositories, mappers, specifications, entities, and exception types; repositories depend on entities.
- Mapping strategy: Use existing MapStruct mapper interfaces with `componentModel = "spring"` for request DTO → entity, entity → response DTO, and in-place update mappings.
Follow the existing architecture and coding patterns.

## 5. Commands
- Build: `.\mvnw.cmd clean package`
- Run tests: `.\mvnw.cmd test`
- Run one test: `.\mvnw.cmd -Dtest=DemoApplicationTests test`
- Run application: `.\mvnw.cmd spring-boot:run`
- Lint or static analysis: [NEEDS CONFIRMATION]
Do not invent commands. Use only commands verified for this project.

## 6. Project-Specific Rules
- Treat current source files as authoritative; use `repomix-output.xml` only as supporting context because it may be outdated.
- Preserve the layered controller → service → repository flow and use DTOs at API boundaries rather than exposing JPA entities.
- Use existing `ApiResponse`, `PageResponse`, `AppException`, `ErrorCode`, and `GlobalExceptionHandler` patterns for API responses and errors.
- Use Jakarta Bean Validation annotations on request DTOs for input validation.
- Preserve the existing customer identity-number uniqueness check.
- Customer deletion is a soft delete that changes customer status to `0`; do not replace it with physical deletion without approval.
- Do not allow customer deletion while an active account with status `1` exists.
- Preserve pagination and name ordering for customer lists and specification-based customer filtering.
- Account and customer status values are represented as integers. Their complete allowed values and transition rules require confirmation before extending status behavior. [NEEDS CONFIRMATION]
- Requirements in `project-problem.md` include transactions, reports, account approval/status operations, CSV export, logging, and test coverage above 75%; do not claim these are implemented unless confirmed in current source.
- Authentication and user-management code exists but is not described in `project-problem.md`; confirm intended scope before expanding it. [NEEDS CONFIRMATION]
- Never expose, copy, log, or commit datasource credentials, token-signing material, passwords, tokens, or other secrets.
- Do not edit generated content under `target/`.
- No Docker configuration currently exists. Do not assume Docker commands or create Docker files without approval.
- No dedicated lint/static-analysis or coverage plugin is configured. Obtain confirmation before adding one. [NEEDS CONFIRMATION]

## 7. Default Working Mode: Plan Only
<!-- KEEP FOR ALL PROJECTS -->
- Always start in PLAN-ONLY mode.
- Inspect the codebase using read-only operations.
- Do not create, modify, delete, move, rename, or format files.
- Do not install dependencies or change project configuration.
- Do not run migrations, code generators, or destructive commands.
- Do not treat the initial task description as permission to edit code.
- First provide an implementation plan and wait for approval.
The plan must include:
1. Understanding of the task.
2. Current behavior and relevant files.
3. Proposed implementation steps.
4. Files expected to change.
5. Risks and assumptions.
6. Verification and testing strategy.
Only edit code after the user explicitly says:
`APPROVED: IMPLEMENT THE PLAN`

## 8. Implementation Rules
<!-- KEEP FOR ALL PROJECTS -->
After approval:
- Modify only the approved scope.
- Preserve existing behavior unless the task requires otherwise.
- Prefer small and focused changes.
- Follow existing naming and coding conventions.
- Do not add dependencies without approval.
- Do not refactor unrelated code.
- Do not hide errors or bypass validation.
- Never expose credentials, tokens, or secrets.
- Stop and request approval if the scope must expand.

## 9. Testing and Verification
<!-- MOSTLY KEEP -->
- Run the smallest relevant tests first.
- Run the full test suite when appropriate.
- Do not change tests merely to make failing code pass.
- Clearly report tests that could not be executed.
- Never claim success without verification evidence.

## 10. Git Rules
<!-- KEEP FOR ALL PROJECTS -->
- Do not create or switch branches unless requested.
- Do not commit, push, merge, or rebase unless requested.
- Do not discard existing user changes.
- Review `git status` and `git diff` before reporting completion.
- Include only task-related changes.

## 11. Completion Report
<!-- KEEP FOR ALL PROJECTS -->
After implementation, report:
1. What was changed.
2. Files that were modified.
3. Tests and commands executed.
4. Verification results.
5. Remaining risks or follow-up work.
6. Changes requiring manual review.
