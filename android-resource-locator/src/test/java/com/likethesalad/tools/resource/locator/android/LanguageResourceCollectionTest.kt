package com.likethesalad.tools.resource.locator.android

import com.google.common.truth.Truth
import com.likethesalad.resource.serializer.android.AndroidResourceSerializer
import com.likethesalad.tools.resource.api.android.AndroidResourceScope
import com.likethesalad.tools.resource.api.android.environment.Language
import com.likethesalad.tools.resource.api.android.environment.Variant
import com.likethesalad.tools.resource.api.android.modules.integer.IntegerAndroidResource
import com.likethesalad.tools.resource.api.android.modules.string.StringAndroidResource
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

    @Test
    fun `Get resources for Language`() {
        val language = Language.Default

        val result = createInstance(getCollectedDir()).getResourcesForLanguage(language)

        Truth.assertThat(result.getAllResources()).containsExactly(
            StringAndroidResource(
                mapOf("name" to "someName", "translatable" to "false"),
                "someValue", AndroidResourceScope(Variant.Default, Language.Default)
            ),
            IntegerAndroidResource(
                "someIntName", 20, AndroidResourceScope(
                    Variant.Custom("demo"),
                    Language.Custom("es")
                )
            )
        )
    }

    private fun createInstance(collectedDir: File): LanguageResourceCollection {
        return LanguageResourceCollection(collectedDir, AndroidResourceSerializer())
    }

    private fun getCollectedDir(): File {
        return DummyResourcesFinder.getResourceFile("collected")
    }
}