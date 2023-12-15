package io.github.cdsap.projectgenerator.files.android

class ValuesColors {
    fun get() = """
        <?xml version="1.0" encoding="utf-8"?>
        <resources>
            <color name="purple_200">#FFBB86FC</color>
            <color name="purple_500">#FF6200EE</color>
            <color name="purple_700">#FF3700B3</color>
            <color name="teal_200">#FF03DAC5</color>
            <color name="teal_700">#FF018786</color>
            <color name="black">#FF000000</color>
            <color name="white">#FFFFFFFF</color>
        </resources>
    """.trimIndent()

    fun getLib(module: String) = """
        <?xml version="1.0" encoding="utf-8"?>
        <resources>
            <color name="${module}_purple_200">#FFBB86FC</color>
            <color name="${module}_purple_500">#FF6200EE</color>
            <color name="${module}_purple_700">#FF3700B3</color>
            <color name="${module}_teal_200">#FF03DAC5</color>
            <color name="${module}_teal_700">#FF018786</color>
            <color name="${module}_black">#FF000000</color>
            <color name="${module}_white">#FFFFFFFF</color>
        </resources>
    """.trimIndent()
}
