# AGENTS.md

Instructions for AI coding agents working in this repository. These conventions are agent-agnostic: they apply to any assistant (CLI tools, IDE integrations, autonomous agents).

## Project overview

CLI and library that generates modularized Gradle projects (Android or JVM) of configurable shape, layer count, and module count. The web generator at https://cdsap.github.io/ProjectGenerator/ wraps the same core.

## Repository layout

- `project-generator/` — library module published to Maven Central as `io.github.cdsap:projectgenerator`. Pure Kotlin/JVM. Contains the generation engine, models (`Versions`, `Project`, `Gradle`, `Shape`, …), and writers under `io/github/cdsap/projectgenerator/`.
- `cli/` — Clikt-based CLI that wraps `project-generator`. Parses YAML overrides via Jackson (`VersionsParser`). Built into a single executable with the `fatbinary` plugin.
- `backend/` — separate backend service (not required for CLI/library work).
- `resources/` — README assets (graph PNGs, demo gif).
- `scripts/` — release/utility scripts.
- `.github/workflows/` — CI (`build.yml` is the main pipeline; matrix over JDK 17/21).

## Build, test, run

Use the Gradle wrapper. The project targets JVM toolchain 23 (set in root `build.gradle.kts`); CI runs tests on JDK 17 and 21.

- **Build the CLI binary**: see [Building the CLI locally](#building-the-cli-locally) below.
- **Unit tests (fast, no project generation)**: `./gradlew :project-generator:unitTest` — registered in `project-generator/build.gradle.kts` as a `Test` task that excludes anything matching `*E2E*`.
- **All tests including E2E**: `./gradlew :project-generator:test` — slow; spawns Gradle TestKit builds.
- **Single test**: `./gradlew :project-generator:test --tests "io.github.cdsap.projectgenerator.ProjectGeneratorE2ETest"` (CI runs each E2E class as its own matrix entry).
- **CLI tests**: `./gradlew :cli:test`.
- **Lint**: `./gradlew ktlintCheck` / `./gradlew ktlintFormat` (root applies the ktlint plugin).

## Building the CLI locally

The CLI is shipped as a single self-contained executable produced by the `fatbinary` Gradle plugin. To build and run it from a fresh checkout:

```bash
./gradlew :cli:fatBinary
./cli/projectGenerator --help
```

`./gradlew :cli:fatBinary` produces the executable at `cli/projectGenerator`. Invoke it directly — for example:

```bash
./cli/projectGenerator generate-project \
  --modules 100 \
  --layers 5 \
  --shape rectangle \
  --type android
```

To rebuild after changing CLI sources, re-run `./gradlew :cli:fatBinary`. Gradle caches up-to-date inputs, so iteration is fast.

Notes:
- Do NOT use `:cli:installDist`, `:cli:assemble`, or `:cli:run` — the published artifact is the fat binary, and other launchers do not match the released distribution.
- The same task runs in CI (`.github/workflows/build.yml`), where it is moved to the repo root as `projectGenerator` before the smoke-test invocations. Locally, leaving it at `cli/projectGenerator` is fine.
- Generated projects land under `projects_generated/` by default (gitignored). Pass `--output-dir` to write elsewhere.

## Code conventions

- **Language**: Kotlin only. No new Java sources.
- **Formatting**: ktlint, applied at the root. Run `ktlintFormat` before committing if you've edited Kotlin.
- **Models are `data class`es with defaults** in `project-generator/src/main/kotlin/io/github/cdsap/projectgenerator/model/`. Defaults live on the data class; parsers should not duplicate them.
- **Versions YAML parsing** lives in `cli/src/main/kotlin/io/github/cdsap/projectgenerator/cli/VersionsParser.kt`. The pattern is: read YAML to a `JsonNode`, normalize fields that Jackson's Kotlin module can't handle (e.g. null → empty string for non-null Kotlin properties), then `treeToValue`. Add a normalization step rather than making model fields nullable.
- **CLI wiring** is in `cli/src/main/kotlin/io/github/cdsap/projectgenerator/cli/Main.kt`. New flags go on `GenerateProjects` (Clikt). CLI args override values from `--versions-file`; preserve that precedence.
- **Don't add comments that restate the code.** Only add a comment when the WHY is non-obvious (workaround, hidden constraint, surprising invariant).

## Tests

- **Unit tests** live alongside the code under `src/test/kotlin/...` in both modules. They must not invoke a real Gradle build.
- **E2E tests** are in `project-generator/src/test/kotlin/.../*E2E*.kt` and use `gradleTestKit()` to actually build a generated project. They are slow and excluded from the `unitTest` task. New E2E tests must include `E2E` in the class name so the unit-test filter excludes them.
- **CLI parser tests** (`cli/src/test/kotlin/.../VersionsParserTest.kt`) are the right place to cover YAML edge cases (null fields, missing keys, legacy enum names). Prefer adding a case there over an end-to-end CLI invocation when verifying parsing behavior.
- When fixing a bug, add a failing test first and confirm it fails, then make it pass.

## Adding a Gradle version

The supported Gradle versions are enumerated in `project-generator/src/main/kotlin/io/github/cdsap/projectgenerator/model/Gradle.kt`. The `update-gradle-versions` workflow automates bumps; manual additions should mirror its output and keep the newest version as the default.

## Adding a CLI flag

1. Add the option to `GenerateProjects` in `Main.kt`.
2. Thread it through `getVersions(...)` or directly into the `ProjectGenerator(...)` constructor.
3. Document it in `README.md` under "CLI Options".
4. Add a CLI test if parsing/precedence is non-trivial.

## Commit and PR conventions

- Match existing commit style (look at `git log`). Short imperative subject, optional body.
- Don't co-author tags unless the user requests them.
- Don't push or open PRs without explicit user instruction.
- Don't bypass hooks (`--no-verify`) or force-push.

## Things that have bitten contributors

- **`develocityUrl: null` in YAML** — Jackson's Kotlin module rejects null for the non-null `Project.develocityUrl: String`. `VersionsParser.normalizeProjectDefaults` rewrites it to `""` before deserialization. Apply the same pattern for any future non-null Kotlin field that users may leave blank in YAML.
- **CLI override precedence** — `--develocity-url` on the command line must win over the YAML value, even when the YAML field is present-but-empty. See `getVersions(...)` in `Main.kt`.
- **AGP 9 default** — the `--agp9` flag is kept only for CLI backwards compatibility. AGP 9 is now the default. Don't reintroduce branching on it.

## What not to do

- Don't create new top-level documentation files unless requested.
- Don't add backwards-compatibility shims for code paths that aren't published. The library is published; the CLI flags are public surface. Internal helpers are not.
- Don't introduce new build plugins or dependency management strategies without discussing first; the project uses a single `libs.versions.toml` catalog under `gradle/`.
- Don't generate projects into the repo root. The default output is `projects_generated/<projectName>/...` and is gitignored; keep it that way.
