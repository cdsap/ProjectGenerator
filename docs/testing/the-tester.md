# TheTester Instructions

## Project Type

ProjectGenerator is a CLI that generates modularized Gradle projects with
different project types, dependency graph shapes, Gradle DSLs, Gradle versions,
and optional features.

The end-to-end test must prove that the CLI binary built from the `main` branch
can generate valid projects and that those generated projects can be built
successfully with their own Gradle wrapper.

TheTester must validate the generated projects themselves, not only the exit
code of the ProjectGenerator CLI.

## Safety

TheTester must test the binary built from the current `main` branch. Do not
download or use a released ProjectGenerator binary.

Before testing:

- Verify that the repository is on `main`.
- Record the commit SHA being tested.
- Do not reset, clean, discard, or overwrite existing user changes.
- Do not publish releases or artifacts.
- Do not publish generated projects to external repositories.
- Do not delete existing projects under `projects_generated/`.

All projects created by TheTester must be placed inside a dedicated test
directory so they can be removed safely.

For example:

```text
projects_generated/the-tester/
```

Prefer using `--output-dir` for every scenario rather than relying on the default
generated-project location.

Cleanup is mandatory even when a test fails.

## Build the Binary

Build ProjectGenerator from the current `main` branch:

```bash
./gradlew cli:fatBinary
```

The expected executable is:

```bash
./cli/projectGenerator
```

Verify that the binary exists and can execute:

```bash
./cli/projectGenerator --help
```

If the binary cannot be built or executed, stop the E2E test and report `FAIL`.

Do not continue testing with an older binary.

## Test Directory

Create one dedicated directory for all generated projects:

```text
projects_generated/the-tester/
```

Each scenario must use its own child directory.

For example:

```text
projects_generated/the-tester/android-kts/
projects_generated/the-tester/jvm-groovy/
projects_generated/the-tester/both-languages/
projects_generated/the-tester/unit-tests/
```

TheTester must only delete directories that it created itself.

## Scenario 1 - Default Android Kotlin DSL Project

Generate a representative Android project using Kotlin DSL.

Example:

```bash
./cli/projectGenerator generate-project \
  --shape rectangle \
  --modules 20 \
  --layers 4 \
  --language kts \
  --type android \
  --output-dir projects_generated/the-tester/android-kts
```

### Generation Validation

Verify that:

- The CLI exits successfully.
- The output directory exists.
- `gradlew` exists.
- `settings.gradle.kts` exists.
- Expected module directories were generated.
- `graph.dot` exists.
- The project contains Gradle build files.
- The generated project is not empty.

### Build Validation

From the generated project:

```bash
./gradlew assembleDebug --no-daemon
```

Verify that:

- Gradle starts successfully.
- Project configuration succeeds.
- All required modules are configured.
- Compilation succeeds.
- `assembleDebug` finishes successfully.
- There are no dependency-resolution, plugin, generated-source, or compilation
  failures.

### Evidence

Capture:

- ProjectGenerator command.
- ProjectGenerator output.
- Generated directory.
- Gradle build command.
- Gradle build result.
- Relevant errors or warnings if present.

## Scenario 2 - JVM Groovy Project

Generate a JVM project using Groovy DSL and a non-default project shape.

Example:

```bash
./cli/projectGenerator generate-project \
  --shape triangle \
  --modules 20 \
  --layers 4 \
  --language groovy \
  --type jvm \
  --output-dir projects_generated/the-tester/jvm-groovy
```

### Generation Validation

Verify that:

- Generation succeeds.
- Groovy Gradle files are generated.
- The expected modules exist.
- `gradlew` exists.
- `graph.dot` exists.

### Build Validation

Run:

```bash
./gradlew build --no-daemon
```

Verify that the complete JVM project builds successfully.

This scenario must prove that ProjectGenerator does not depend on
Android-specific behavior and that Groovy DSL generation is valid.

## Scenario 3 - Both Gradle DSLs

Generate one project using:

```text
--language both
```

Example:

```bash
./cli/projectGenerator generate-project \
  --shape rhombus \
  --modules 20 \
  --layers 4 \
  --language both \
  --type jvm \
  --output-dir projects_generated/the-tester/both-languages
```

### Expected Behavior

The output must contain:

```text
project_kts/
project_groovy/
```

TheTester must build both projects independently.

### Kotlin DSL Validation

Run inside `project_kts`:

```bash
./gradlew build --no-daemon
```

Verify that the build succeeds.

### Groovy DSL Validation

Run inside `project_groovy`:

```bash
./gradlew build --no-daemon
```

Verify that the build succeeds.

The scenario fails if only one of the two generated projects builds
successfully.

## Scenario 4 - Generated Unit Tests and Alternate Gradle Version

Generate a JVM project with generated unit tests and a supported Gradle version
different from the default.

Example:

```bash
./cli/projectGenerator generate-project \
  --shape inverse_triangle \
  --modules 20 \
  --layers 4 \
  --language kts \
  --type jvm \
  --generate-unit-test \
  --gradle 8.14.5 \
  --output-dir projects_generated/the-tester/unit-tests
```

### Validation

Verify that:

- Generation succeeds.
- Unit test sources are generated.
- The Gradle wrapper uses the requested Gradle version.
- The generated project builds successfully.
- Generated tests compile.
- Generated tests execute successfully.

Run:

```bash
./gradlew build --no-daemon
```

The scenario fails if the project compiles but its generated tests fail.

## Additional Feature Coverage

When changes on `main` affect these features, TheTester should add targeted
scenarios for them rather than relying only on the standard matrix:

- `--room-database`
- `--android-kotlin-multiplatform-library`
- `--di hilt`
- `--di metro`
- `--di none`
- `--classes-module-type random`
- `--type-of-string-resources large`
- `--versions-file`
- Different supported Gradle versions
- Different dependency graph shapes

TheTester does not need to test every possible Cartesian combination.

The goal is to select combinations that exercise meaningfully different
generation paths.

When testing a specific bug fix or feature, add a scenario that directly
exercises that behavior.

## Shape Validation

ProjectGenerator supports multiple dependency graph shapes.

Across the complete E2E execution, test more than one shape.

Representative shapes include:

- `rectangle`
- `triangle`
- `inverse_triangle`
- `rhombus`
- `flat`
- `middle_bottleneck`

For every generated project, verify that `graph.dot` is produced.

When the scenario specifically concerns graph generation, inspect the generated
graph and verify that it is non-empty and represents the requested shape.

It is not necessary to build six large projects on every execution unless the
change affects graph generation.

## Generated Project Validation

A generated project is not considered valid simply because ProjectGenerator
returned exit code `0`.

For every scenario verify:

- The expected output directory exists.
- A Gradle wrapper exists.
- Settings files exist.
- Build files exist.
- Module directories were generated.
- `graph.dot` exists.
- The generated Gradle project can be configured.
- The appropriate Gradle build task completes successfully.

For Android projects, prefer:

```bash
./gradlew assembleDebug --no-daemon
```

For JVM projects, prefer:

```bash
./gradlew build --no-daemon
```

When tests are generated, ensure those tests are executed as part of the
validation.

## Failure Handling

If project generation fails:

1. Capture the ProjectGenerator command and output.
2. Mark the scenario as `FAIL`.
3. Continue with other independent scenarios when possible.
4. Cleanup the generated directory.

If the generated project fails to build:

1. Capture the Gradle command.
2. Capture the failing task.
3. Capture the relevant exception and stack trace.
4. Identify whether the failure occurred during:
   - Gradle configuration
   - Plugin resolution
   - Dependency resolution
   - Compilation
   - Code generation
   - Testing
   - Packaging
5. Mark the scenario as `FAIL`.
6. Cleanup the generated project.

Do not modify a generated project to make it build.

The generated output itself is what is under test.

## Cleanup

Cleanup is a required part of the test.

After all scenarios have completed, including failed scenarios, remove every
project directory created by TheTester.

For example:

```bash
rm -rf projects_generated/the-tester
```

Only remove the dedicated TheTester directory.

Do not run:

```bash
rm -rf projects_generated
```

because that directory may contain projects created by the user or other
processes.

After cleanup verify that:

```text
projects_generated/the-tester
```

no longer exists.

Cleanup must happen even when:

- Project generation fails.
- A generated project fails to build.
- One scenario is blocked.
- TheTester terminates testing early because of another failure.

Capture required logs and evidence before deleting generated projects.

## General Acceptance Criteria

The ProjectGenerator E2E test passes only when:

- The CLI binary is built successfully from `main`.
- The binary executes successfully.
- Multiple representative project configurations are generated.
- Android generation is validated.
- JVM generation is validated.
- Kotlin DSL generation is validated.
- Groovy DSL generation is validated.
- `--language both` produces two valid projects.
- At least one non-default generation path is exercised.
- Generated Gradle wrappers execute successfully.
- Every generated project selected for validation builds successfully.
- Generated tests execute successfully when requested.
- Expected files and modules are actually created.
- No generated project is manually modified to make the test pass.
- All projects generated by TheTester are removed when testing finishes.

A successful ProjectGenerator CLI exit code alone is not sufficient.

## Final Report Requirements

TheTester must include:

- Result: `PASS`, `FAIL`, or `BLOCKED`.
- `main` branch commit SHA tested.
- CLI binary build result.
- Scenarios executed.
- Exact ProjectGenerator command used for each scenario.
- Project type for each scenario.
- Language/DSL for each scenario.
- Shape and module count.
- Gradle version when relevant.
- Generated project build command.
- Build result for each generated project.
- Relevant Gradle failure information when applicable.
- Any blocked dependency or missing environment requirement.
- Confirmation that all projects created during testing were removed.

Example summary:

```text
Result: PASS
Commit: abc1234

CLI binary:
PASS - ./gradlew cli:fatBinary

Scenarios:
PASS - Android / KTS / rectangle / 20 modules
PASS - JVM / Groovy / triangle / 20 modules
PASS - JVM / both DSLs / rhombus / 20 modules
PASS - JVM / KTS / generated tests / Gradle 8.14.5

Cleanup:
PASS - projects_generated/the-tester removed
```

If any generated project fails to build, the overall result must be `FAIL`.
