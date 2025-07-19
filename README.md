# Project Generator

A CLI tool to bootstrap Android or JVM Gradle projects with a customizable module graph.

## Installation
```bash
curl -L https://github.com/cdsap/ProjectGenerator/releases/download/v0.2.1/projectGenerator --output projectGenerator
chmod 0757 projectGenerator
```

## Commands
- **generate-project** (default)
  Generates a project. Example:
  ```bash
  ./projectGenerator generate-project --modules 100
  ```
- **generate-yaml-versions**
  Produces a template `versions.yaml` file. The file can then be passed to `generate-project` via `--versions-file`.

## Options
Key flags for `generate-project`:
- `--shape`          triangle, rhombus, flat, rectangle, middle_bottleneck, inverse_triangle (default: rectangle)
- `--modules`        required number of modules
- `--layers`         number of layers (default: 5)
- `--language`       kts (default), groovy, both
- `--type`           android (default) or jvm
- `--classes-module` number of classes per module (default: 5)
- `--classes-module-type` fixed (default) or random
- `--type-of-string-resources` normal (default) or large
- `--generate-unit-test` generate unit tests (default: false)
- `--gradle`         gradle_8_2, gradle_8_5, gradle_8_9, gradle_8_13, gradle_8_14_3 (default)
- `--develocity`     enable Develocity build scan plugin
- `--versions-file`  path to a custom YAML versions file

Example with several options:
```bash
./projectGenerator generate-project --shape rhombus --modules 50 --layers 4 --language both --type jvm --classes-module 10 --classes-module-type random --type-of-string-resources large --generate-unit-test --gradle gradle_8_9 --develocity --versions-file my_versions.yaml
```

## Library usage
```kotlin
val modules = 50
val shape = Shape.RHOMBUS
ProjectGenerator(
    modules = modules,
    shape = shape,
    language = Language.KTS,
    typeOfProjectRequested = TypeProjectRequested.ANDROID,
    classesPerModule = ClassesPerModule(ClassesPerModuleType.FIXED, 20),
    versions = Versions(project = Project(jdk = "17")),
    typeOfStringResources = TypeOfStringResources.LARGE,
    layers = 5,
    generateUnitTest = true,
    gradle = GradleWrapper(Gradle.GRADLE_8_14_3),
    path = file.path
).write()
```
Add the dependency to use the generator programmatically:
```kotlin
implementation("io.github.cdsap:projectgenerator:0.2.1")
```

## Versions file example
```yaml
project:
  develocity: 4.0.1
  jdk: 23
kotlin:
  kgp: 2.1.20
  ksp: 2.1.20-2.0.0
  coroutines: 1.7.3
android:
  agp: 8.9.1
```
See `resources/versions.yaml` for a complete list.

## Libraries used
- [clikt](https://github.com/ajalt/clikt)
