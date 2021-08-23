package com.likethesalad.tools.resource.locator.android

import com.google.common.truth.Truth
import com.likethesalad.tools.resource.api.android.environment.Language
import com.likethesalad.tools.testing.DummyResourcesFinder
import org.junit.Test
import java.io.File

class LanguageResourceCollectionTest {

    @Test
    fun `Get list of languages available`() {
        val instance = createInstance(getCollectedDir())

        Truth.assertThat(instance.listLanguages()).containsExactly(
            Language.Default,
            Language.Custom("es")
        )
    }

    private fun createInstance(collectedDir: File): LanguageResourceCollection {
        return LanguageResourceCollection(collectedDir)
    }

    private fun getCollectedDir(): File {
        return DummyResourcesFinder.getResourceFile("collected")
    }
}