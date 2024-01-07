package com.likethesalad.tools.resource.locator.android.extension.configuration.source.utils

import com.likethesalad.tools.agpcompat.api.bridges.AndroidExtension
import com.likethesalad.tools.resource.collector.android.data.variant.VariantTree
import com.likethesalad.tools.resource.locator.android.di.LocatorScope
import com.likethesalad.tools.resource.locator.android.extension.configuration.source.impl.AndroidGeneratedSourceConfiguration
import com.likethesalad.tools.resource.locator.android.extension.configuration.source.impl.AndroidLibrariesSourceConfiguration
import com.likethesalad.tools.resource.locator.android.extension.configuration.source.impl.AndroidRawSourceConfiguration
import com.likethesalad.tools.resource.locator.android.providers.TaskFinder
import javax.inject.Inject

@LocatorScope
class CommonSourceConfigurationCreator @Inject constructor(
    private val androidExtension: AndroidExtension,
    private val taskFinder: TaskFinder
) {

    fun createAndroidRawConfiguration(variantTree: VariantTree): AndroidRawSourceConfiguration {
        return AndroidRawSourceConfiguration(androidExtension, variantTree)
    }

    fun createAndroidGeneratedResConfiguration(variantTree: VariantTree): AndroidGeneratedSourceConfiguration {
        return AndroidGeneratedSourceConfiguration(variantTree, taskFinder)
    }

    fun createAndroidAndroidLibrariesConfiguration(variantTree: VariantTree): AndroidLibrariesSourceConfiguration {
        return AndroidLibrariesSourceConfiguration(variantTree)
    }
}