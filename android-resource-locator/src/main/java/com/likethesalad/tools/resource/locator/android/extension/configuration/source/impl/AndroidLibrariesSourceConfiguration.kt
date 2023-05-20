package com.likethesalad.tools.resource.locator.android.extension.configuration.source.impl

import com.likethesalad.tools.resource.api.android.environment.Variant
import com.likethesalad.tools.resource.collector.android.data.resdir.ResDir
import com.likethesalad.tools.resource.collector.android.data.variant.VariantTree
import com.likethesalad.tools.resource.locator.android.di.ResourceLocatorComponent
import com.likethesalad.tools.resource.locator.android.di.ResourceLocatorComponentProvider
import com.likethesalad.tools.resource.locator.android.extension.configuration.source.base.ResDirResourceSourceConfiguration
import com.likethesalad.tools.resource.locator.android.providers.InstancesProvider
import org.gradle.api.file.FileCollection
import org.gradle.api.provider.Provider
import java.io.File

open class AndroidLibrariesSourceConfiguration(
    variantTree: VariantTree
) : ResDirResourceSourceConfiguration(variantTree) {

    private lateinit var libraryResources: FileCollection
    private val instancesProvider:InstancesProvider by lazy {
        ResourceLocatorComponentProvider.getComponent().instancesProvider()
    }

    override fun getResDirs(): List<ResDir> {
        val resDirs = mutableListOf<ResDir>()

        for (file in libraryResources.files) {
            val resDir = ResDir(Variant.Dependency, file)
            resDirs.add(resDir)
        }

        return resDirs
    }

    override fun getSourceFiles(): Provider<Iterable<File>> {
        return instancesProvider.createProvider { getLibrariesResourcesFileCollection() }
    }

    protected open fun getLibrariesResourcesFileCollection(): FileCollection {
        libraryResources = variantTree.androidVariantData.getLibrariesResources()
        return libraryResources
    }
}