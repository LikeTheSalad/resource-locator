package com.likethesalad.tools.resource.locator.android.extension.configuration

import com.likethesalad.tools.resource.collector.android.data.resdir.ResDirFinder
import com.likethesalad.tools.resource.collector.android.data.variant.VariantTree
import com.likethesalad.tools.resource.collector.android.di.CollectorComponentProvider
import com.likethesalad.tools.resource.collector.android.source.providers.VariantTreeResourceSourceProvider
import com.likethesalad.tools.resource.collector.filter.ResourceSourceFilterRule
import com.likethesalad.tools.resource.collector.source.ResourceSourceProvider
import com.likethesalad.tools.resource.locator.android.extension.data.ResourceLocatorConfiguration

open class DefaultResourceLocatorConfiguration : ResourceLocatorConfiguration {

    override fun getSourceFilterRules(variantTree: VariantTree): List<ResourceSourceFilterRule<*>> {
        return emptyList()
    }

    override fun getSourceProviders(variantTree: VariantTree): List<ResourceSourceProvider> {
        val variantTreeResourceProvider = createVariantTreeResourceProvider(
            variantTree,
            ResDirFinder.newInstance()
        )

        return listOf(variantTreeResourceProvider)
    }

    private fun createVariantTreeResourceProvider(
        variantTree: VariantTree,
        resDirFinder: ResDirFinder
    ): VariantTreeResourceSourceProvider {
        return CollectorComponentProvider.getComponent()
            .variantTreeResourceSourceProviderFactory()
            .create(variantTree, resDirFinder)
    }
}