package com.likethesalad.tools.resource.collector.android.source.providers

import com.google.common.truth.Truth
import com.likethesalad.tools.resource.api.Resource
import com.likethesalad.tools.resource.api.android.environment.Language
import com.likethesalad.tools.resource.api.android.environment.Variant
import com.likethesalad.tools.resource.api.android.impl.AndroidResourceScope
import com.likethesalad.tools.resource.collector.android.data.resdir.ResDir
import com.likethesalad.tools.resource.collector.source.ResourceSource
import com.likethesalad.tools.testing.BaseMockable
import com.likethesalad.tools.testing.DummyResourcesFinder
import org.junit.Test
import java.io.File

class ResDirResourceSourceProviderTest : BaseMockable() {

    @Test
    fun `Provide android xml resource sources`() {
        val variant = Variant.Default
        val resDir = ResDir(Variant.Default, getResFolder("main/res"))

        val sources = getInstance(resDir).getSources()

        Truth.assertThat(sources.size).isEqualTo(2)
        checkSourceIn(
            ResourceSourceExpected(
                getResFolder("main/res/values/strings.xml"),
                AndroidResourceScope(variant, Language.Default)
            ), sources
        )
        checkSourceIn(
            ResourceSourceExpected(
                getResFolder("main/res/values-es/strings.xml"),
                AndroidResourceScope(variant, Language.Custom("es"))
            ), sources
        )
    }

    private fun getInstance(resDir: ResDir): ResDirResourceSourceProvider {
        return ResDirResourceSourceProvider(
            resDir
        )
    }

    private fun checkSourceIn(
        expectedSource: ResourceSourceExpected,
        sources: List<ResourceSource>
    ) {
        Truth.assertThat(sources.any {
            it.getSource() == expectedSource.file && it.getScope() == expectedSource.scope
        }).isTrue()
    }

    private fun getResFolder(relativePath: String): File {
        return DummyResourcesFinder.getResourceFile("resourceProvider/variants/$relativePath")
    }

    data class ResourceSourceExpected(val file: File, val scope: Resource.Scope)
}