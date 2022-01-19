package com.likethesalad.tools.resource.locator.android.test.locator

import com.likethesalad.tools.resource.collector.android.data.variant.VariantTree
import com.likethesalad.tools.resource.locator.android.extension.configuration.ResourceLocatorEntryPoint
import com.likethesalad.tools.resource.locator.android.extension.configuration.data.ResourceLocatorInfo
import com.likethesalad.tools.resource.locator.android.extension.configuration.source.ResourceSourceConfiguration
import com.likethesalad.tools.resource.locator.android.extension.configuration.source.utils.CommonSourceConfigurationCreator

class TestLocatorEntryPoint(private val commonSourceConfigurationCreator: CommonSourceConfigurationCreator) :
    ResourceLocatorEntryPoint {

    override fun getResourceSourceConfigurations(variantTree: VariantTree): List<ResourceSourceConfiguration> {
        return listOf(
            commonSourceConfigurationCreator.createAndroidRawConfiguration(variantTree),
            commonSourceConfigurationCreator.createAndroidGeneratedResConfiguration(variantTree),
            commonSourceConfigurationCreator.createAndroidAndroidLibrariesConfiguration(variantTree)
        )
    }

    override fun onLocatorCreated(info: ResourceLocatorInfo) {
        // No operation
    }
}