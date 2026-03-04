package io.github.cdsap.projectgenerator.model

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class ClassesPerModuleTest {

    @Test
    fun `reject values lower than minimum`() {
        assertThrows<IllegalArgumentException> {
            ClassesPerModule(classes = 9)
        }
    }
}
