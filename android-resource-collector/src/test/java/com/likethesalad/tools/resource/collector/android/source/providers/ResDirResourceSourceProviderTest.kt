package com.likethesalad.tools.resource.collector.android.source.providers

import com.google.common.truth.Truth
import com.likethesalad.tools.resource.api.ResourceScope
import com.likethesalad.tools.resource.api.android.AndroidResourceScope
import com.likethesalad.tools.resource.api.android.environment.Language
import com.likethesalad.tools.resource.api.android.environment.Variant
import com.likethesalad.tools.resource.collector.android.data.AndroidXmlResDocument
import com.likethesalad.tools.resource.collector.android.data.resdir.ResDir
import com.likethesalad.tools.resource.collector.android.data.valuedir.ValueDirFinder
import com.likethesalad.tools.resource.collector.android.data.xml.XmlFileFinder
import com.likethesalad.tools.resource.collector.android.source.AndroidXmlResourceSource
import com.likethesalad.tools.resource.collector.source.ResourceSource
import com.likethesalad.tools.testing.BaseMockable
import com.likethesalad.tools.testing.DummyResourcesFinder
import io.mockk.every
import io.mockk.impl.annotations.MockK
import org.junit.Before
import org.junit.Test
import java.io.File
import javax.xml.parsers.DocumentBuilderFactory

class ResDirResourceSourceProviderTest : BaseMockable() {

    @MockK
    lateinit var androidXmlResourceSourceFactory: AndroidXmlResourceSource.Factory

    @Before
    fun setUp() {
        every { androidXmlResourceSourceFactory.create(any(), any()) } answers {
            AndroidXmlResourceSource(
                firstArg(),
                secondArg(),
                AndroidXmlResDocument.Factory(DocumentBuilderFactory.newInstance())
            )
        }
    }

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
            resDir,
            ValueDirFinder(),
            XmlFileFinder(),
            androidXmlResourceSourceFactory
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

    data class ResourceSourceExpected(val file: File, val scope: ResourceScope)
}