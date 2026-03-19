package io.github.cdsap.projectgenerator.cli

import com.fasterxml.jackson.annotation.JsonSetter
import com.fasterxml.jackson.annotation.Nulls
import com.fasterxml.jackson.databind.MapperFeature
import com.fasterxml.jackson.databind.node.ObjectNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.module.kotlin.KotlinModule
import io.github.cdsap.projectgenerator.model.Gradle
import io.github.cdsap.projectgenerator.model.VersionsFile
import java.io.File

object VersionsParser {

    private val mapper = ObjectMapper(YAMLFactory()).apply {
        registerModule(KotlinModule())
        enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS)
        configOverride(List::class.java).setterInfo = JsonSetter.Value.forValueNulls(Nulls.AS_EMPTY)
    }

    /**
     * Parses a YAML file into [VersionsFile].
     * [VersionsFile] keeps plugin list fields nullable so omitted YAML keys can resolve to empty lists
     * instead of inheriting the runtime defaults from [io.github.cdsap.projectgenerator.model.Versions].
     */
    fun fromFile(file: File): VersionsFile {
        val tree = mapper.readTree(file)
        normalizeGradleVersion(tree)
        normalizeProjectDefaults(tree)
        return mapper.treeToValue(tree, VersionsFile::class.java)
    }

    private fun normalizeGradleVersion(tree: com.fasterxml.jackson.databind.JsonNode) {
        if (tree !is ObjectNode) return
        val gradleNode = tree.get("gradle") ?: return
        if (!gradleNode.isTextual) return

        val normalized = Gradle.fromValue(gradleNode.asText()).name
        tree.put("gradle", normalized)
    }

    private fun normalizeProjectDefaults(tree: com.fasterxml.jackson.databind.JsonNode) {
        if (tree !is ObjectNode) return
        val projectNode = tree.get("project")
        if (projectNode !is ObjectNode) return

        if (projectNode.get("develocityUrl")?.isNull == true) {
            projectNode.put("develocityUrl", "")
        }
    }
}
