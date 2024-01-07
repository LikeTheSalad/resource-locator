package com.likethesalad.tools.resource.locator.android.extension.configuration.source.impl

import com.likethesalad.tools.resource.collector.android.data.resdir.ResDirFinder
import com.likethesalad.tools.resource.collector.android.data.variant.VariantTree
import com.likethesalad.tools.resource.collector.android.source.providers.VariantTreeResourceSourceProvider
import com.likethesalad.tools.resource.collector.source.ResourceSourceProvider
import com.likethesalad.tools.resource.locator.android.di.ResourceLocatorComponentProvider
import com.likethesalad.tools.resource.locator.android.extension.configuration.source.ResourceSourceConfiguration
import com.likethesalad.tools.resource.locator.android.providers.InstancesProvider
import org.gradle.api.provider.Provider
import java.io.File

class AndroidRawSourceConfiguration(
    variantTree: VariantTree,
    resDirFinder: ResDirFinder,
    private val variantTreeResourceSourceProviderFactory: VariantTreeResourceSourceProvider.Factory
) : ResourceSourceConfiguration(variantTree) {

    private val instancesProvider:InstancesProvider by lazy {
        ResourceLocatorComponentProvider.getComponent().instancesProvider()
    }
    private val variantTreeResourceProvider: VariantTreeResourceSourceProvider by lazy {
        createVariantTreeResourceProvider(
            variantTree,
            resDirFinder
        )
    }

    override fun doGetSourceProviders(): List<ResourceSourceProvider> {
        return listOf(variantTreeResourceProvider)
    }

    override fun getSourceFiles(): Provider<Iterable<File>> {
        return instancesProvider.createProvider {
            variantTreeResourceProvider.getSources().map { it.getSource() as File }
        }
    }

    private fun createVariantTreeResourceProvider(
        variantTree: VariantTree,
        resDirFinder: ResDirFinder
    ): VariantTreeResourceSourceProvider {
        return VariantTreeResourceSourceProvider(variantTree, resDirFinder)
    }
}