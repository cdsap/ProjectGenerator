# Project Generator
CLI generating modularized Gradle projects based on different shapes

![Demo](resources/demo.gif)

If you need to create small or medium projects (<300 modules), you can use the web generator: https://cdsap.github.io/ProjectGenerator/


# Usage
## CLI
### Install
```
curl -L https://github.com/cdsap/ProjectGenerator/releases/download/v0.3.5/projectGenerator  --output projectGenerator
chmod 0757 projectGenerator
```


### Modes

#### 1. Generate Project (default)
```bash
./projectGenerator generate-project --modules 100
```
This command generates a project with the specified shape, layers, and modules. All options below can be combined with this mode.

#### 2. Generate YAML Versions File
```bash
./projectGenerator generate-yaml-versions
```

This command generates a `versions.yaml` file template for custom dependency versions.
Then, you can use the versions.yaml in the `generate-project` command:
```
./projectGenerator generate-project --shape triangle --layers 5 --modules 100 --versions-file versions.yaml
```

### CLI Options
- `--shape`: triangle, rhombus, flat, rectangle, middle_bottleneck, inverse_triangle (default: rectangle)
- `--modules` (required): Number of modules to create
- `--layers`: Number of layers (default: 5)
- `--language`: kts (default), groovy, both
- `--type`: android (default), jvm
- `--classes-module`: Number of classes per module (default: 5)
- `--classes-module-type`: fixed (default), random
- `--type-of-string-resources`: normal (default), large
- `--generate-unit-test`: Generate unit tests (default: false)
- `--gradle`: gradle_8_13, gradle_8_14_3, gradle_9_0_0, gradle_9_1_0, gradle_9_2_0 (default: gradle_9_2_0)
- `--develocity`: Enables the Develocity build scan plugin (default: false). If --develocity-url is not specified, the build scan will be published to Gradle Scans.
- `--develocity-url`: Specify Develocity URL
- `--versions-file`: Path to a custom YAML file with dependency versions
- `--project-name`: Name of the project

#### Example: Generate a project with custom options
```bash
./projectGenerator generate-project --shape rhombus --modules 50 --layers 4 --language both --type jvm --classesModule 10 --classesModuleType random --typeOfStringResources large --generateUnitTest --gradle gradle_8_9 --develocity --versionsFile ./my_versions.yaml
```

## Library
```kotlin
val modules = 50
val shape = Shape.RHOMBUS
ProjectGenerator(
    modules = 50,
    shape =shape,
    language = Language.KTS,
    typeOfProjectRequested = TypeProjectRequested.ANDROID,
    classesPerModule = ClassesPerModule(ClassesPerModuleType.FIXED, 20),
    versions = Versions(project = Project(jdk = "17")),
    typeOfStringResources = TypeOfStringResources.LARGE,
    layers = 5,
    generateUnitTest = true,
    gradle = GradleWrapper(Gradle.GRADLE_9_2_0),
    path = file.path
).write()

```
### Dependency
```
  implementation("io.github.cdsap:projectgenerator:0.3.5")
```

# Options

## `modules`
Number of modules to create in the project
## `layers`
Number of layers where the modules will be distributed.
## `shape`
Defines the shape of the project dependency graph based on the distribution of modules by layer. When generating a new
project, a `graph.dot` file is created in the root project folder containing the project dependency graph.
Current shapes supported:
### `triangle`
![Triangle](resources/triangle.png)
### `inverse_triangle`
![Triangle](resources/inverse_triangle.png)
### `rhombus`
![Rhombus](resources/rhombus.png)
### `rectangle` (default)
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
./projectGenerator  generate-project  --shape triangle --layers 5 --modules 100 --language groovy
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
./projectGenerator  generate-project  --shape triangle --layers 5 --modules 100 --type jvm
```

## `Classes Module`
Classes generated per module, options:
* classes: Classes to generate per module. Default 5.
* type:
  * Fixed(default): Each module will create n classes where n represents the argument `classes`.
  * Random: Each module will create `Random.nextInt(2, classesPerModule.classes)` classes.

#### Example
```kotlin
./projectGenerator  generate-project  --shape triangle --layers 5 --modules 100 --classes-module-type random --classes-module 150
```

## String Resources type
### Normal
Each module generated includes 8 string resources in the file `strings.xml`. **Default**.

### Large
Each module generated includes 900 string resources in the file `strings.xml`

##### Example
```kotlin
./projectGenerator  generate-project  --shape triangle --layers 5 --modules 100 --type-of-string-resources large
```
## Generate Unit Test
**default false**

If enabled, each module will generate n unit tests, where n is the argument `classes`

##### Example
```kotlin
./projectGenerator  generate-project  --shape triangle --layers 5 --modules 100 --generate-unit-test true
```
## Gradle
Gradle used, versions supported:
* Gradle 8.5
* Gradle 8.9
* Gradle 8.13
* Gradle 8.14_2
* Gradle 9.0.0 **default**

##### Example
```kotlin
./projectGenerator  generate-project  --shape triangle --layers 5 --modules 100 --gradle 8.13
```


## Versions
Example output versions.yaml:
```yaml
project:
    develocity: 4.1
    jdk: 23
kotlin:
    kgp: 2.2.0
    ksp: 2.2.0-2.0.2
    coroutines: 1.10.2
    kotlinProcessor:
        processor: KSP
android:
    agp: 8.12.0
    androidxCore: 1.16.0
    appcompat: 1.7.1
    material: 1.12.0
    lifecycle: 2.9.2
    fragment: 1.8.8
    activity: 1.10.1
    constraintlayout: 2.2.1
    work: 2.10.3
    hilt: 2.56.2
    hiltAandroidx: 1.2.0
    composeBom: 2025.07.00
testing:
    junit4: 4.13.2
    junit5: 5.13.2
    truth: 1.4.4
    mockk: 1.14.5
    coreTesting: 2.2.0
    junitExt: 1.3.0
additionalSettingsPlugins:

additionalBuildGradleRootPlugins:
    - id: com.autonomousapps.dependency-analysis
      version: 2.19.0
      apply: true
```



## Libraries used
* [clikt](https://github.com/ajalt/clikt)
