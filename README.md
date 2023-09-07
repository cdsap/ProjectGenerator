## Project Generator
CLI Tool generating Gradle projects with different project graph shapes

## Simple Usage
```
 curl -L https://github.com/cdsap/ProjectGenerator/releases/download/v.0.1.1/projectGenerator --output projectGenerator
 chmod 0757 projectGenerator
./projectGenerator --shape triangle --language kts --modules 100
```
This command generates a project with 100 modules using Kotlin DSL.
The project is divided in 5 layers + 1 layer acting as a main entry point of the project.
The project generated guarantee relationships for project dependencies in the contiguous layers.

## Options
### `--shape`
Defines the type of project graph generated. Based on the distribution of modules by layer.
Current shapes supported:
#### `triangle`
![Triangle](resources/triangle.png)
#### `rhombus`
![Rhombus](resources/rhombus.png)
#### `rectangle`
![Rectangle](resources/rectangle.png)
#### `flat`
![Flat](resources/flat.png)
#### `middle_bottleneck`
![Flat](resources/middle_bottleneck.png)

### `--language`
#### `kts`
The project generated includes modules build scripts in Kotlin DSL
#### `groovy`
The project generated includes modules build scripts in Groovy
#### `both`
Two projects will be generated using Kotlin DSL and Groovy

### `--modules`
Number of modules to be generated in the project

## Project
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
