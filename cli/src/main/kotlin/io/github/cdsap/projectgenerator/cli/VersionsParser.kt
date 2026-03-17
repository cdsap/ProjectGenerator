package io.github.cdsap.projectgenerator.cli

import com.fasterxml.jackson.annotation.JsonSetter
import com.fasterxml.jackson.annotation.Nulls
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.module.kotlin.KotlinModule
import io.github.cdsap.projectgenerator.model.VersionsFile
import io.github.cdsap.projectgenerator.model.Versions
import java.io.File

object VersionsParser {

    private val mapper = ObjectMapper(YAMLFactory()).apply {
        registerModule(KotlinModule())
        configOverride(List::class.java).setterInfo = JsonSetter.Value.forValueNulls(Nulls.AS_EMPTY)
    }

    /**
     * Parses a YAML file into [Versions].
     * [VersionsFile] keeps plugin list fields nullable so omitted YAML keys can resolve to empty lists
     * instead of inheriting the runtime defaults from [Versions].
     */
    fun fromFile(file: File): Versions = mapper.readValue(file, VersionsFile::class.java).resolve()
}
