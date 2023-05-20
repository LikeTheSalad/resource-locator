package com.likethesalad.tools.resource.locator.android.extension.configuration.source.utils

import com.likethesalad.tools.resource.collector.android.data.resdir.ResDirFinder
import com.likethesalad.tools.resource.collector.android.data.variant.VariantTree
import com.likethesalad.tools.resource.collector.android.source.providers.VariantTreeResourceSourceProvider
import com.likethesalad.tools.resource.locator.android.di.LocatorScope
import com.likethesalad.tools.resource.locator.android.extension.configuration.source.impl.AndroidGeneratedSourceConfiguration
import com.likethesalad.tools.resource.locator.android.extension.configuration.source.impl.AndroidLibrariesSourceConfiguration
import com.likethesalad.tools.resource.locator.android.extension.configuration.source.impl.AndroidRawSourceConfiguration
import com.likethesalad.tools.resource.locator.android.providers.InstancesProvider
import com.likethesalad.tools.resource.locator.android.providers.TaskFinder
import javax.inject.Inject

@LocatorScope
class CommonSourceConfigurationCreator @Inject constructor(
    private val taskFinder: TaskFinder,
    private val resDirFinder: ResDirFinder,
    private val variantTreeResourceSourceProviderFactory: VariantTreeResourceSourceProvider.Factory,
    private val instancesProvider: InstancesProvider
) {

    fun createAndroidRawConfiguration(variantTree: VariantTree): AndroidRawSourceConfiguration {
        return AndroidRawSourceConfiguration(variantTree, resDirFinder, variantTreeResourceSourceProviderFactory, instancesProvider)
    }

    fun createAndroidGeneratedResConfiguration(variantTree: VariantTree): AndroidGeneratedSourceConfiguration {
        return AndroidGeneratedSourceConfiguration(variantTree, taskFinder)
    }

    fun createAndroidAndroidLibrariesConfiguration(variantTree: VariantTree): AndroidLibrariesSourceConfiguration {
        return AndroidLibrariesSourceConfiguration(variantTree, instancesProvider)
    }
}