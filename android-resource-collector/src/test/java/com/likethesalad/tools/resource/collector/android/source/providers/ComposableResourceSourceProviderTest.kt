package com.likethesalad.tools.resource.collector.android.source.providers

import com.google.common.truth.Truth
import com.likethesalad.tools.resource.collector.source.ResourceSource
import com.likethesalad.tools.resource.collector.source.ResourceSourceProvider
import com.likethesalad.tools.testing.BaseMockable
import io.mockk.every
import org.junit.Assert.fail
import org.junit.Test

class ComposableResourceSourceProviderTest : BaseMockable() {

    @Test
    fun `Adding source providers`() {
        val sources1 = listOf<ResourceSource>(mockk())
        val sources2 = listOf<ResourceSource>(mockk(), mockk())
        val provider1 = createProviderFor(sources1)
        val provider2 = createProviderFor(sources2)

        val composable = ComposableResourceSourceProvider()
        composable.addProvider(provider1)
        composable.addProvider(provider2)

        Truth.assertThat(composable.getSources()).containsExactlyElementsIn(
            sources1 + sources2
        )
    }

    @Test
    fun `Throw error if provider already added`() {
        val provider = createProviderFor(listOf())

        val composable = ComposableResourceSourceProvider()
        composable.addProvider(provider)
        try {
            composable.addProvider(provider)
            fail()
        } catch (e: IllegalArgumentException) {
            Truth.assertThat(e.message)
                .isEqualTo("Provider already added: $provider")
        }
    }

    private fun createProviderFor(sources: List<ResourceSource>): ResourceSourceProvider {
        val provider = mockk<ResourceSourceProvider>()
        every { provider.getSources() }.returns(sources)
        return provider
    }
}