# AGENTS.md — Bank_App

## Stack (actual, verified from code)
- Java 21, Spring Boot 3.5.15, Maven
- DB: MySQL, `ddl-auto=update` — **no Flyway** (no migration history, no version control on schema)
- Auth: `spring-security-crypto` only (BCrypt). **No `spring-boot-starter-security`**, no `SecurityFilterChain`.
- JWT: hand-rolled via `nimbus-jose-jwt` (`MACSigner`/`MACVerifier`, HS512) — not Spring Security OAuth2 Resource Server.
- Test: only `DemoApplicationTests.contextLoads()`. No Testcontainers, no business logic tests.

## Package structure (actual)
Root: `com.example.demo`, organized **by layer**, not by domain:
```
entity/  repository/  service/  service/impl/  controller/
dto/request/  dto/response/  mapper/  exception/  specification/
```
No `config/`, `security/`, `aspect/`, `util/` packages exist. When creating new
infra code (SecurityConfig, filters, JWT provider), follow this same layer
convention — don't introduce a domain-based restructure unprompted.

## ⚠️ Known issues (fix when touching related code — don't silently ignore)

Ranked by risk. Do not "fix" these proactively outside the task scope unless
asked — flag them instead. Exception: if a task touches this exact code path,
fix it as part of that task rather than adding to a growing pile.

1. **[Critical] No endpoint protection.** No `SecurityFilterChain`, no JWT
   filter (`OncePerRequestFilter`). Every controller is currently open —
   `/auth/introspect` exists but nothing enforces it on other endpoints.
2. **[Critical] Hardcoded JWT signing key** in
   `AuthenticationServiceImpl.java:36` (`SIGNER_KEY`). Must move to env var
   before this goes anywhere near production.
3. **[High] `Account.balance` is `Double`**, not `BigDecimal`. Any task
   touching balance/money math must convert this field, not just patch
   around it.
4. **[High] No Flyway** — schema drifts silently via `ddl-auto=update`.
   Adding Flyway is a prerequisite for any real migration work.
5. **[Medium] No `@Transactional`** on `AccountServiceImpl` operations.
6. **[Medium] `GlobalExceptionHandler`** calls `exception.printStackTrace()`
   in the generic `Exception.class` handler — replace with proper logging
   before shipping.
7. **[Medium] No refresh token, no revoke mechanism.** Access token only,
   1h expiry, no way to invalidate on logout/password change.
8. **[Low] No Testcontainers, no integration tests.** Add when writing new
   service-layer logic in the module you're touching — don't attempt to
   backfill the whole app in one task.

## Coding rules

1. **Never invent API/annotations you're unsure about.** Call `context7` for
   current docs before writing code involving a specific library — don't
   guess from memory.
2. **Security baseline for any new code:**
    - No hardcoded secrets/credentials — env vars only (this is stricter than
      what currently exists; don't replicate the existing hardcoded key
      pattern in new code).
    - Validate all client input at the DTO layer (`@Valid` + Bean Validation).
    - Parameterized queries / JPA only — no string-concatenated SQL.
    - New money-related fields/params use `BigDecimal`, never `float`/`double`.
    - Never log sensitive data (card numbers, passwords, full tokens).
3. **Error handling**: use the existing `AppException` + `ErrorCode` +
   `GlobalExceptionHandler` pattern — don't introduce a second error-handling
   mechanism.
4. **Transactions**: wrap new balance/transaction-affecting service methods
   in `@Transactional` explicitly.
5. **Don't change architecture unprompted** — stay consistent with the
   existing layer-based structure.

## Tool usage rules

- Before writing code involving a specific API/library: call `context7` for
  the correct version's docs.
- After generating/editing code: run `semgrep` (rules `p/security-audit`,
  `p/owasp-top-ten`, `p/secrets`) before reporting done. Fix findings, don't
  silently ignore them.
- For context spanning multiple files, use `repomix` scoped to the relevant
  layer directory (e.g. `service/impl/**`, `controller/**`) with `--compress`
  instead of reading every file manually. Given the layer-based structure,
  scope by layer + filename pattern rather than by domain folder (there are
  no domain folders).

## Project context (repomix)

- Output files live in `docs/context/` (gitignored):
    - `repomix-auth.xml` — files matching `*Auth*`, `*Security*`, `*Jwt*` across all layers
    - `repomix-account.xml` — files matching `*Account*` across all layers
    - `repomix-full.xml` — whole repo, last resort for cross-cutting tasks only
- Regenerate only when structure changes meaningfully — not every task.
- Treat as a stale snapshot; verify against the real file if anything looks off.

## Required workflow per task

1. Read this file + the relevant code only.
2. If ambiguous or multi-module, write a short plan before editing; confirm
   before schema or API-contract changes.
3. Small, scoped edits — don't touch unrelated files in the same pass.
4. After coding: run `semgrep`, run relevant tests, self-check before
   reporting done.
5. Never auto-commit/push — leave the diff for review.

## Testing

- New business logic ships with a unit test — no exceptions.
- When adding integration tests, use Testcontainers (not yet in the
  project — add the dependency as part of the first task that needs it,
  not preemptively).

## Boundaries

- Don't add new dependencies unless explicitly requested — ask first.
- Don't change Spring Boot/Java version on your own.
- Don't delete existing tests to force a pass.
- Don't silently fix items in "Known issues" outside the current task's
  scope — flag them in your response instead.