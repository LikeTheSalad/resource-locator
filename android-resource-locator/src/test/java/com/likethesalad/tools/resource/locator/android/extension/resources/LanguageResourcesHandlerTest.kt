package com.likethesalad.tools.resource.locator.android.extension.resources

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

class LanguageResourcesHandlerTest {

    @Test
    fun `Get list of languages available`() {
        val instance = createInstance(getCollectedDir())

        Truth.assertThat(instance.listLanguages()).containsExactly(
            Language.Default,
            Language.Custom("es")
        )
    }

    @Test
    fun `Get resources for default Language`() {
        val language = Language.Default

        val result = createInstance(getCollectedDir()).getMergedResourcesForLanguage(language)

        Truth.assertThat(result.getAllResources()).containsExactly(
            StringAndroidResource(
                mapOf("name" to "someName", "translatable" to "false"),
                "someValue", AndroidResourceScope(Variant.Default, Language.Default)
            ),
            StringAndroidResource(
                mapOf("name" to "someOtherName"),
                "some other value", AndroidResourceScope(Variant.Default, Language.Default)
            ),
            StringAndroidResource(
                mapOf("name" to "someNameFromLib"),
                "some lib value", AndroidResourceScope(Variant.Dependency, Language.Default)
            ),
            IntegerAndroidResource(
                "someIntName", 20, AndroidResourceScope(
                    Variant.Custom("demo"),
                    Language.Default
                )
            )
        )
    }

    @Test
    fun `Get resources for custom Language`() {
        val language = Language.Custom("es")

        val result = createInstance(getCollectedDir()).getMergedResourcesForLanguage(language)

        Truth.assertThat(result.getAllResources()).containsExactly(
            StringAndroidResource(
                mapOf("name" to "someName", "translatable" to "false"),
                "someValue", AndroidResourceScope(Variant.Default, Language.Default)
            ),
            StringAndroidResource(
                mapOf("name" to "someOtherName"),
                "otro valor", AndroidResourceScope(Variant.Default, language)
            ),
            StringAndroidResource(
                mapOf("name" to "someNameFromLib"),
                "some lib value", AndroidResourceScope(Variant.Dependency, Language.Default)
            ),
            StringAndroidResource(
                mapOf("name" to "someIntName"),
                "Not an int", AndroidResourceScope(Variant.Custom("demo"), language)
            ),
            StringAndroidResource(
                mapOf("name" to "someSpanishOnlyValue"),
                "Hola", AndroidResourceScope(Variant.Default, language)
            ),
            IntegerAndroidResource(
                "someIntName", 20, AndroidResourceScope(
                    Variant.Custom("demo"),
                    Language.Default
                )
            )
        )
    }

    @Test
    fun `Return default resources if language specific ones aren't found`() {
        val instance = createInstance(getCollectedDir())
        val defaultResources = instance.getMergedResourcesForLanguage(Language.Default)

        val result = instance.getMergedResourcesForLanguage(Language.Custom("jp"))

        Truth.assertThat(result).isEqualTo(defaultResources)
    }

    private fun createInstance(collectedDir: File): LanguageResourcesHandler {
        return LanguageResourcesHandler(DirLanguageCollectorProvider(collectedDir, AndroidResourceSerializer()))
    }

    private fun getCollectedDir(): File {
        return DummyResourcesFinder.getResourceFile("collected")
    }
}