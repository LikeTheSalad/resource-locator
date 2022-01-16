package com.likethesalad.tools.resource.locator.android.extension.configuration.source.impl

import com.likethesalad.tools.resource.collector.android.data.resdir.ResDirFinder
import com.likethesalad.tools.resource.collector.android.data.variant.VariantTree
import com.likethesalad.tools.resource.collector.android.di.CollectorComponentProvider
import com.likethesalad.tools.resource.collector.android.source.providers.VariantTreeResourceSourceProvider
import com.likethesalad.tools.resource.collector.source.ResourceSourceProvider
import com.likethesalad.tools.resource.locator.android.extension.configuration.source.ResourceSourceConfiguration
import java.io.File

class AndroidRawSourceConfiguration(variantTree: VariantTree) : ResourceSourceConfiguration(variantTree) {

    private val variantTreeResourceProvider: VariantTreeResourceSourceProvider by lazy {
        createVariantTreeResourceProvider(
            variantTree,
            ResDirFinder.newInstance()
        )
        variantTreeResourceProvider.addFilterRules(sourceFilterRules)
        variantTreeResourceProvider
    }

    override fun getSourceProviders(): List<ResourceSourceProvider> {
        return listOf(variantTreeResourceProvider)
    }

    override fun getSourceFiles(): Iterable<File> {
        return variantTreeResourceProvider.getSources().map { it.getSource() as File }
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