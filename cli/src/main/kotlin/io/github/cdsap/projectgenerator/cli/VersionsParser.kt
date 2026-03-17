package io.github.cdsap.projectgenerator.cli

import com.fasterxml.jackson.annotation.JsonSetter
import com.fasterxml.jackson.annotation.Nulls
import com.fasterxml.jackson.databind.MapperFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.module.kotlin.KotlinModule
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
    fun fromFile(file: File): VersionsFile = mapper.readValue(file, VersionsFile::class.java)
}
