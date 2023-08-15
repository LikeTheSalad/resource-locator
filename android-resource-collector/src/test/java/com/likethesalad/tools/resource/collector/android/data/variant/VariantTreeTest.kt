package com.likethesalad.tools.resource.collector.android.data.variant

import com.google.common.truth.Truth
import com.likethesalad.tools.agpcompat.api.bridges.AndroidVariantData
import com.likethesalad.tools.resource.api.android.environment.Variant
import com.likethesalad.tools.testing.BaseMockable
import io.mockk.every
import io.mockk.impl.annotations.MockK
import org.junit.Test

class VariantTreeTest : BaseMockable() {

    @MockK
    lateinit var variantData: AndroidVariantData

    @Test
    fun `Get variants from empty flavors`() {
        validateTreeOrder("debug", emptyList(), "debug", "main", "debug")
        validateTreeOrder("release", emptyList(), "release", "main", "release")
    }

    @Test
    fun `Get variants from raw string with one flavor`() {
        validateTreeOrder(
            "fullDebug",
            listOf("full"), "debug",
            "main", "full", "debug", "fullDebug"
        )
    }

    @Test
    fun `Get variants from raw string with multiple flavors`() {
        validateTreeOrder(
            "demoStableDebug",
            listOf("demo", "stable"), "debug",
            "main", "stable", "demo",
            "demoStable", "debug", "demoStableDebug"
        )

        validateTreeOrder(
            "demoStablePaidDebug",
            listOf("demo", "stable", "paid"), "debug",
            "main", "paid", "stable", "demo",
            "demoStablePaid", "debug", "demoStablePaidDebug"
        )

        validateTreeOrder(
            "demoStablePaidAnimeDebug",
            listOf("demo", "stable", "paid", "anime"), "debug",
            "main", "anime", "paid", "stable", "demo",
            "demoStablePaidAnime", "debug", "demoStablePaidAnimeDebug"
        )
    }

    @Test
    fun `Compare variants`() {
        val subject = Variant.Custom("demoStable")
        val child = Variant.Custom("debug")
        val parent = Variant.Custom("demo")
        val variantTree = createInstance(
            "demoStableDebug",
            listOf("demo", "stable"), "debug"
        )

        val comparison = variantTree.check(subject)

        Truth.assertThat(comparison.isChildOf(child)).isFalse()
        Truth.assertThat(comparison.isChildOf(parent)).isTrue()
        Truth.assertThat(comparison.isChildOf(subject)).isFalse()
    }

    private fun validateTreeOrder(
        variantName: String,
        flavors: List<String>,
        suffix: String,
        vararg expectedVariantNames: String
    ) {
        val expectedVariants = expectedVariantNames.map { Variant.Custom(it) }

        val variantTree = createInstance(variantName, flavors, suffix)

        Truth.assertThat(variantTree.getVariants()).containsExactlyElementsIn(expectedVariants).inOrder()
    }

    private fun createInstance(
        variantName: String,
        flavors: List<String>,
        suffix: String
    ): VariantTree {
        every { variantData.getVariantName() }.returns(variantName)
        every { variantData.getVariantFlavors() }.returns(flavors)
        every { variantData.getVariantType() }.returns(suffix)

        return VariantTree(variantData)
    }
}