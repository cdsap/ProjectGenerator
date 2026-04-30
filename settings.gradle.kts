plugins {
    id("com.gradle.develocity") version "4.4.1"
    id("com.gradle.common-custom-user-data-gradle-plugin") version "2.6.0"
}

develocity {
    server = "https://community.develocity.cloud"
    allowUntrustedServer = true
    projectId.set("cdsap/ProjectGenerator")
    buildScan {
        uploadInBackground.set(false)
    }
}

rootProject.name = "ProjectGenerator"
include("project-generator")
include("cli")

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
