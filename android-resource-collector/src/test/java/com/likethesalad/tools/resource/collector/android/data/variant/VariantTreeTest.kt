package com.likethesalad.tools.resource.collector.android.data.variant

import com.google.common.truth.Truth
import com.likethesalad.tools.resource.api.android.environment.Variant
import com.likethesalad.tools.resource.collector.android.helpers.AndroidVariantHelper
import com.likethesalad.tools.testing.BaseMockable
import io.mockk.every
import io.mockk.impl.annotations.MockK
import org.junit.Test


class VariantTreeTest : BaseMockable() {

    @MockK
    lateinit var variantHelper: AndroidVariantHelper

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

    private fun validateTreeOrder(
        variantName: String,
        flavors: List<String>,
        suffix: String,
        vararg expectedVariantNames: String
    ) {
        val expectedVariants = expectedVariantNames.map { Variant.Custom(it) }
        every { variantHelper.getVariantName() }.returns(variantName)
        every { variantHelper.getVariantFlavors() }.returns(flavors)
        every { variantHelper.getVariantType() }.returns(suffix)

        val variantTree = VariantTree(variantHelper)

        Truth.assertThat(variantTree.getVariants()).containsExactlyElementsIn(expectedVariants).inOrder()
    }
}