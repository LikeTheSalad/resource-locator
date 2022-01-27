package com.likethesalad.tools.resource.locator.android.extension.configuration.source.impl

import com.likethesalad.tools.resource.api.android.environment.Variant
import com.likethesalad.tools.resource.collector.android.data.resdir.ResDir
import com.likethesalad.tools.resource.collector.android.data.variant.VariantTree
import com.likethesalad.tools.resource.collector.android.source.providers.ResDirResourceSourceProvider
import com.likethesalad.tools.resource.collector.source.ResourceSourceProvider
import com.likethesalad.tools.resource.locator.android.extension.configuration.source.ResourceSourceConfiguration
import com.likethesalad.tools.resource.locator.android.utils.AndroidResourcesHelper
import org.gradle.api.file.FileCollection
import java.io.File

class AndroidLibrariesSourceConfiguration(
    variantTree: VariantTree,
    private val resDirSourceProviderFactory: ResDirResourceSourceProvider.Factory
) : ResourceSourceConfiguration(variantTree) {

    private lateinit var libraryResources: FileCollection

    override fun doGetSourceProviders(): List<ResourceSourceProvider> {
        val providers = mutableListOf<ResourceSourceProvider>()

        for (file in libraryResources.files) {
            val provider = resDirSourceProviderFactory.create(ResDir(Variant.Dependency, file))
            providers.add(provider)
        }

        return providers
    }

    override fun getSourceFiles(): Iterable<File> {
        libraryResources = AndroidResourcesHelper.getLibrariesResourceFileCollection(variantTree.androidVariantData)
        return libraryResources
    }
}