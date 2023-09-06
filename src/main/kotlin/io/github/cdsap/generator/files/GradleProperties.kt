package io.github.cdsap.generator.files

class GradleProperties {
    fun get() = """
        org.gradle.jvmargs=-Xmx5g -XX:+HeapDumpOnOutOfMemoryError -Dfile.encoding=UTF-8 -XX:+UseParallelGC -XX:MaxMetaspaceSize=1g
    """.trimIndent()
}
