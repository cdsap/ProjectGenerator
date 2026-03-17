package io.github.cdsap.projectgenerator.model

data class VersionsFile(
    val kotlin: Kotlin = Kotlin(),
    val android: Android = Android(),
    val testing: Testing = Testing(),
    val project: Project = Project(),
    val di: DependencyInjection = DependencyInjection.HILT,
    val additionalBuildGradleRootPlugins: List<AdditionalPlugin>? = null,
    val additionalSettingsPlugins: List<AdditionalPlugin>? = null
) {
    fun resolve(base: Versions = Versions()): Versions = base.copy(
        kotlin = kotlin,
        android = android,
        testing = testing,
        project = project,
        di = di,
        additionalBuildGradleRootPlugins = additionalBuildGradleRootPlugins ?: emptyList(),
        additionalSettingsPlugins = additionalSettingsPlugins ?: emptyList()
    )
}
