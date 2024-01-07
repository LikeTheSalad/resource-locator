package com.likethesalad.tools.resource.locator.android.extension.configuration.source.impl

import com.likethesalad.tools.agpcompat.api.bridges.AndroidExtension
import com.likethesalad.tools.resource.collector.android.data.variant.VariantTree
import com.likethesalad.tools.resource.collector.android.source.providers.VariantTreeResourceSourceProvider
import com.likethesalad.tools.resource.collector.source.ResourceSourceProvider
import com.likethesalad.tools.resource.locator.android.di.ResourceLocatorComponentProvider
import com.likethesalad.tools.resource.locator.android.extension.configuration.source.ResourceSourceConfiguration
import com.likethesalad.tools.resource.locator.android.providers.InstancesProvider
import org.gradle.api.provider.Provider
import java.io.File

class AndroidRawSourceConfiguration(
    private val androidExtension: AndroidExtension,
    variantTree: VariantTree
) : ResourceSourceConfiguration(variantTree) {

    private val instancesProvider: InstancesProvider by lazy {
        ResourceLocatorComponentProvider.getComponent().instancesProvider()
    }
    private val variantTreeResourceProvider: VariantTreeResourceSourceProvider by lazy {
        createVariantTreeResourceProvider(
            variantTree
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
        variantTree: VariantTree
    ): VariantTreeResourceSourceProvider {
        return VariantTreeResourceSourceProvider(androidExtension, variantTree)
    }
}