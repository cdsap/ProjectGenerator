# Project Generator
CLI generating modularized Gradle projects based on different shapes
<p float="left">
  <img src="resources/example_readme.png" width="500" height="200"/>
  <img src="resources/project2.png" height="200" />
</p>


# Usage
## CLI

### Modes

#### 1. Generate Project (default)
```bash
./projectGenerator generate-project --shape triangle --layers 5 --modules 100
```
This command generates a project with the specified shape, layers, and modules. All options below can be combined with this mode.

#### 2. Generate YAML Versions File
```bash
./projectGenerator generate-yaml-versions
```
This command generates a `versions.yaml` file template for custom dependency versions.

### CLI Options
- `--shape` (required): triangle, rhombus, flat, rectangle, middle_bottleneck, inverse_triangle
- `--modules` (required): Number of modules to create
- `--layers`: Number of layers (default: 5)
- `--language`: kts (default), groovy, both
- `--type`: android (default), jvm
- `--classesModule`: Number of classes per module (default: 5)
- `--classesModuleType`: fixed (default), random
- `--typeOfStringResources`: normal (default), large
- `--generateUnitTest`: Generate unit tests (default: false)
- `--gradle`: gradle_8_2, gradle_8_5, gradle_8_9, gradle_8_13, gradle_8_14_2 (default: gradle_8_14_2)
- `--develocity`: Enable Develocity build scan plugin (default: false)
- `--versionsFile`: Path to a custom YAML file with dependency versions

#### Example: Generate a project with custom options
```bash
./projectGenerator generate-project --shape rhombus --modules 50 --layers 4 --language both --type jvm --classesModule 10 --classesModuleType random --typeOfStringResources large --generateUnitTest --gradle gradle_8_9 --develocity --versionsFile ./my_versions.yaml
```

### Legacy (default) mode
You can still use the legacy CLI without specifying a mode:
```bash
./projectGenerator --shape triangle --layers 5 --modules 100
```
This is equivalent to `generate-project` mode.

## Library
```kotlin
ProjectGenerator(
    modules = 50,
    shape = Shape.MIDDLE_BOTTLENECK,
    layers = 5
).write()

```
### Dependency
```
  implementation("io.github.cdsap:projectgenerator:0.2.0")
```

# Options

## `modules`
Number of modules to create in the project
## `layers`
Number of layers where the modules will be distributed.
## `shape`
Defines the shape of the project dependency graph based on the distribution of modules by layer.
Current shapes supported:
### `triangle`
![Triangle](resources/triangle.png)
### `inverse_triangle`
![Triangle](resources/inverse_triangle.png)
### `rhombus`
![Rhombus](resources/rhombus.png)
### `rectangle`
![Rectangle](resources/rectangle.png)
### `flat`
![Flat](resources/flat.png)
### `middle_bottleneck`
![Flat](resources/middle_bottleneck.png)

## `language`
### `kts`
**default**

The project generated includes modules build scripts in Kotlin DSL
### `groovy`
The project generated includes modules build scripts in Groovy
### `both`
Two projects will be generated using Kotlin DSL and Groovy

#### Example
```kotlin
// dependency
ProjectGenerator(
    modules = 50,
    shape = Shape.MIDDLE_BOTTLENECK,
    layers = 5,
    language = Language.GROOVY
).write()

// cli
./projectGenerator --shape triangle --layers 5 --modules 100 --language groovy
```
## `type`
Type of project generated:
### `android`
**default**

Android project
### `jvm`
Kotlin-JVM project

#### Example
```kotlin
// dependency
ProjectGenerator(
    modules = 50,
    shape = Shape.MIDDLE_BOTTLENECK,
    layers = 5,
    typeOfProjectRequested = TypeProjectRequested.JVM
).write()

// cli
./projectGenerator --shape triangle --layers 5 --modules 100 --type jvm
```

## `Classes Module`
Classes generated per module, options:
* classes: Classes to generate per module. Default 5.
* type:
  * Fixed(default): Each module will create n classes where n represents the argument `classes`.
  * Random: Each module will create `Random.nextInt(2, classesPerModule.classes)` classes.

#### Example
```kotlin
// dependency
ProjectGenerator(
    modules = 50,
    layers = 5,
    shape = Shape.INVERSE_TRIANGLE,
    classesPerModule = ClassesPerModule(ClassesPerModuleType.RANDOM,100)
)

// cli
./projectGenerator --shape triangle --layers 5 --modules 100 --classes-module-type random --classes-module 150
```
### AGP/KGP
#### `AGP version`


Android Gradle Plugin version (default 8.9.0)
##### Example
```kotlin
// dependency
ProjectGenerator(
    modules = 50,
    layers = 5,
    shape = Shape.INVERSE_TRIANGLE,
    versions = Versions(agp = "8.1.3")
)

// cli
./projectGenerator --shape triangle --layers 5 --modules 100 --agp-version 8.1.3
```

#### `KGP version`
Kotlin Gradle Plugin version (default 2.1.20)

##### Example
```kotlin
// dependency
ProjectGenerator(
    modules = 50,
    layers = 5,
    shape = Shape.INVERSE_TRIANGLE,
    versions = Versions(kgp = "1.9.0")
)

// cli
./projectGenerator --shape triangle --layers 5 --modules 100 --kgp-version 1.9.0
```

## String Resources type
### Normal
Each module generated includes 8 string resources in the file `strings.xml`. **Default**.

### Large
Each module generated includes 900 string resources in the file `strings.xml`

##### Example
```kotlin
// dependency
ProjectGenerator(
    modules = 50,
    layers = 5,
    shape = Shape.INVERSE_TRIANGLE,
    typeOfStringResources = TypeOfStringResources.LARGE
)

// cli
./projectGenerator --shape triangle --layers 5 --modules 100 --type-of-string-rresources large
```
## Generate Unit Test
**default false**

If enabled, each module will generate n unit tests, where n is the argument `classes`

##### Example
```kotlin
// dependency
ProjectGenerator(
    modules = 50,
    layers = 5,
    shape = Shape.INVERSE_TRIANGLE,
    generateUnitTest = true
)

// cli
./projectGenerator --shape triangle --layers 5 --modules 100 --generate-unit-test true
```
## Gradle
Gradle used, versions supported:
* Gradle 8.2
* Gradle 8.5
* Gradle 8.9
* Gradle 8.13 **default**

##### Example
```kotlin
// dependency
ProjectGenerator(
    modules = 50,
    layers = 5,
    shape = Shape.INVERSE_TRIANGLE,
    gradle = GradleWrapper(Gradle.GRADLE_7_6_2)
)

// cli
./projectGenerator --shape triangle --layers 5 --modules 100 --gradle 7.6.2
```

## Dependency Plugins
**default false**

Includes in the root build gradle the plugins:
- [jraska/modules-graph-assert](https://github.com/jraska/modules-graph-assert)
- [autonomousapps/dependency-analysis-gradle-plugin](https://github.com/autonomousapps/dependency-analysis-gradle-plugin)
- [siggijons/graph-untangler-plugin](https://github.com/siggijons/graph-untangler-plugin)

##### Example
```kotlin
// dependency
ProjectGenerator(
    modules = 50,
    layers = 5,
    shape = Shape.INVERSE_TRIANGLE,
    dependencyPlugins = true
)

// cli
./projectGenerator --shape triangle --layers 5 --modules 100 --dependency-plugins true
```

## Extending Project
The default project generated is composed by the modules and one convention plugin defined as composite build.
This convention plugin is used in all the modules of the project.

If you want to extend the build logic used in the project:

#### Main root Build Gradle
[BuildGradle.kt](src%2Fmain%2Fkotlin%2Fio%2Fgithub%2Fcdsap%2Fgenerator%2Ffiles%2FBuildGradle.kt)
#### Gradle properties
[GradleProperties.kt](src%2Fmain%2Fkotlin%2Fio%2Fgithub%2Fcdsap%2Fgenerator%2Ffiles%2FGradleProperties.kt)
#### Root Settings Gradle
[SettingsGradle.kt](src%2Fmain%2Fkotlin%2Fio%2Fgithub%2Fcdsap%2Fgenerator%2Ffiles%2FSettingsGradle.kt)
#### Build Gradle composite build
[CompositeBuildBuildGradle.kt](src%2Fmain%2Fkotlin%2Fio%2Fgithub%2Fcdsap%2Fgenerator%2Ffiles%2FCompositeBuildBuildGradle.kt)
#### Convention Plugin used in the modules
[CompositeBuildPlugin1.kt](src%2Fmain%2Fkotlin%2Fio%2Fgithub%2Fcdsap%2Fgenerator%2Ffiles%2FCompositeBuildPlugin1.kt)
#### Settings Gradle composite build
[CompositeBuildSettingsGradle.kt](src%2Fmain%2Fkotlin%2Fio%2Fgithub%2Fcdsap%2Fgenerator%2Ffiles%2FCompositeBuildSettingsGradle.kt)

## Libraries used
* [clikt](https://github.com/ajalt/clikt)
