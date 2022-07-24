package com.likethesalad.tools.resource.locator.android.extension.resources

import com.google.common.truth.Truth
import com.likethesalad.resource.serializer.android.AndroidResourceSerializer
import com.likethesalad.tools.resource.api.android.impl.AndroidResourceScope
import com.likethesalad.tools.resource.api.android.environment.Language
import com.likethesalad.tools.resource.api.android.environment.Variant
import com.likethesalad.tools.resource.api.android.modules.string.StringAndroidResource
import com.likethesalad.tools.testing.DummyResourcesFinder
import org.junit.Test
import java.io.File

class DirLanguageCollectorProviderTest {

    private val provider = DirLanguageCollectorProvider(getCollectedDir(), AndroidResourceSerializer())

    @Test
    fun `Getting languages available from files in dir`() {
        Truth.assertThat(provider.listLanguages()).containsExactly(
            Language.Default,
            Language.Custom("es")
        )
    }

    @Test
    fun `Getting resource collection from language file`() {
        val language = Language.Custom("es")
        Truth.assertThat(provider.getCollectionByLanguage(language)!!.getAllResources()).containsExactly(
            StringAndroidResource(
                "someIntName",
                "Not an int",
                AndroidResourceScope(Variant.Custom("demo"), language)
            ),
            StringAndroidResource(
                "someSpanishOnlyValue",
                "Hola",
                AndroidResourceScope(Variant.Default, language)
            ),
            StringAndroidResource(
                "someOtherName",
                "otro valor",
                AndroidResourceScope(Variant.Default, language)
            )
        )
    }

    @Test
    fun `Getting null when file doesn't exist for specified language`() {
        Truth.assertThat(provider.getCollectionByLanguage(Language.Custom("it"))).isNull()
    }

    private fun getCollectedDir(): File {
        return DummyResourcesFinder.getResourceFile("collected")
    }
}