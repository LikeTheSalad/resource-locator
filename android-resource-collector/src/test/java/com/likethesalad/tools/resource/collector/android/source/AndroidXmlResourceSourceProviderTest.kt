package com.likethesalad.tools.resource.collector.android.source

import com.google.common.truth.Truth
import com.likethesalad.tools.resource.api.ResourceScope
import com.likethesalad.tools.resource.api.android.AndroidResourceScope
import com.likethesalad.tools.resource.api.android.environment.Language
import com.likethesalad.tools.resource.api.android.environment.Variant
import com.likethesalad.tools.resource.collector.android.data.resdir.ResDirFinder
import com.likethesalad.tools.resource.collector.android.data.valuedir.ValueDirFinder
import com.likethesalad.tools.resource.collector.android.data.variant.VariantTree
import com.likethesalad.tools.resource.collector.android.data.xml.XmlFileFinder
import com.likethesalad.tools.resource.collector.android.helpers.AndroidExtensionHelper
import com.likethesalad.tools.resource.collector.source.ResourceSource
import com.likethesalad.tools.testing.BaseMockable
import com.likethesalad.tools.testing.DummyResourcesFinder.getResourceFile
import io.mockk.every
import io.mockk.impl.annotations.MockK
import org.junit.Before
import org.junit.Test
import java.io.File

class AndroidXmlResourceSourceProviderTest : BaseMockable() {

    @MockK
    lateinit var variantTree: VariantTree

    @MockK
    lateinit var androidExtensionHelper: AndroidExtensionHelper

    private val main = Variant.Default
    private val demo = Variant.Custom("demo")
    private val paid = Variant.Custom("paid")

    private lateinit var androidXmlResourceSourceProvider: AndroidXmlResourceSourceProvider

    @Before
    fun setUp() {
        androidXmlResourceSourceProvider = AndroidXmlResourceSourceProvider(
            variantTree, ResDirFinder(androidExtensionHelper),
            ValueDirFinder(), XmlFileFinder()
        )
    }

    @Test
    fun `Provide android xml resource sources`() {
        prepareVariantTree()

        val sources = androidXmlResourceSourceProvider.getSources()

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
            androidExtensionHelper.getVariantSrcDirs(variant.name)
        }.returns(srcDirs)
    }

    data class ResourceSourceExpected(val file: File, val scope: ResourceScope)
}