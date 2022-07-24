package com.likethesalad.tools.resource.collector.android.source.providers

import com.google.common.truth.Truth
import com.likethesalad.tools.android.plugin.data.AndroidExtension
import com.likethesalad.tools.resource.api.Resource
import com.likethesalad.tools.resource.api.android.AndroidResourceScope
import com.likethesalad.tools.resource.api.android.environment.Language
import com.likethesalad.tools.resource.api.android.environment.Variant
import com.likethesalad.tools.resource.collector.android.data.AndroidXmlResDocument
import com.likethesalad.tools.resource.collector.android.data.resdir.ResDirFinder
import com.likethesalad.tools.resource.collector.android.data.valuedir.ValueDirFinder
import com.likethesalad.tools.resource.collector.android.data.variant.VariantTree
import com.likethesalad.tools.resource.collector.android.data.xml.XmlFileFinder
import com.likethesalad.tools.resource.collector.android.source.AndroidXmlResourceSource
import com.likethesalad.tools.resource.collector.source.ResourceSource
import com.likethesalad.tools.testing.BaseMockable
import com.likethesalad.tools.testing.DummyResourcesFinder.getResourceFile
import io.mockk.every
import io.mockk.impl.annotations.MockK
import org.junit.Before
import org.junit.Test
import java.io.File
import javax.xml.parsers.DocumentBuilderFactory

class VariantTreeResourceSourceProviderTest : BaseMockable() {

    @MockK
    lateinit var variantTree: VariantTree

    @MockK
    lateinit var androidExtension: AndroidExtension

    @MockK
    lateinit var resDirResourceSourceProviderFactory: ResDirResourceSourceProvider.Factory

    @MockK
    lateinit var androidXmlResourceSourceFactory: AndroidXmlResourceSource.Factory

    private val main = Variant.Default
    private val demo = Variant.Custom("demo")
    private val paid = Variant.Custom("paid")

    @Before
    fun setUp() {
        every { androidXmlResourceSourceFactory.create(any(), any()) } answers {
            AndroidXmlResourceSource(
                firstArg(),
                secondArg(),
                AndroidXmlResDocument.Factory(DocumentBuilderFactory.newInstance())
            )
        }
        every { resDirResourceSourceProviderFactory.create(any()) } answers {
            ResDirResourceSourceProvider(
                firstArg(), ValueDirFinder(), XmlFileFinder(),
                androidXmlResourceSourceFactory
            )
        }
    }

    @Test
    fun `Provide android xml resource sources`() {
        prepareVariantTree()

        val sources = getInstance().getSources()

        Truth.assertThat(sources.size).isEqualTo(6)
        checkSourceIn(
            ResourceSourceExpected(
                getVariantFile("demo/res/values/strings.xml"),
                AndroidResourceScope(demo, Language.Default)
            ), sources
        )
        checkSourceIn(
            ResourceSourceExpected(
                getVariantFile("demo/res/values-it/strings.xml"),
                AndroidResourceScope(demo, Language.Custom("it"))
            ), sources
        )
        checkSourceIn(
            ResourceSourceExpected(
                getVariantFile("main/res/values/strings.xml"),
                AndroidResourceScope(main, Language.Default)
            ), sources
        )
        checkSourceIn(
            ResourceSourceExpected(
                getVariantFile("main/res/values-es/strings.xml"),
                AndroidResourceScope(main, Language.Custom("es"))
            ), sources
        )
        checkSourceIn(
            ResourceSourceExpected(
                getVariantFile("main/res2/values/strings.xml"),
                AndroidResourceScope(main, Language.Default)
            ), sources
        )
        checkSourceIn(
            ResourceSourceExpected(
                getVariantFile("paid/res/values-es/strings.xml"),
                AndroidResourceScope(paid, Language.Custom("es"))
            ), sources
        )
    }

    private fun getInstance(): VariantTreeResourceSourceProvider {
        return VariantTreeResourceSourceProvider(
            variantTree,
            ResDirFinder(androidExtension),
            resDirResourceSourceProviderFactory
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

    private fun prepareVariantTree() {
        val variants = listOf(
            main, demo, paid
        )
        every {
            variantTree.getVariants()
        }.returns(variants)

        stubSrcDirsFor(main, setOf(getVariantFile("main/res"), getVariantFile("main/res2")))
        stubSrcDirsFor(demo, setOf(getVariantFile("demo/res")))
        stubSrcDirsFor(paid, setOf(getVariantFile("paid/res")))
    }

    private fun getVariantFile(relativePath: String): File {
        return getResourceFile("resourceProvider/variants/$relativePath")
    }

    private fun stubSrcDirsFor(variant: Variant, srcDirs: Set<File>) {
        every {
            androidExtension.getVariantSrcDirs(variant.name)
        }.returns(srcDirs)
    }

    data class ResourceSourceExpected(val file: File, val scope: Resource.Scope)
}