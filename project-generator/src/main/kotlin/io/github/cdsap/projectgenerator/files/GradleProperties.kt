package io.github.cdsap.projectgenerator.files

class GradleProperties {
    fun get() = """
        org.gradle.jvmargs=-Xmx5g -XX:+HeapDumpOnOutOfMemoryError -Dfile.encoding=UTF-8 -XX:+UseParallelGC -XX:MaxMetaspaceSize=1g
        android.useAndroidX=true
        org.gradle.cache=true
    """.trimIndent()
}
