package com.likethesalad.tools.resource.locator.android.extension.configuration

import com.likethesalad.tools.resource.collector.android.data.variant.VariantTree
import com.likethesalad.tools.resource.locator.android.extension.configuration.data.ResourceLocatorTaskInfo
import com.likethesalad.tools.resource.locator.android.extension.configuration.source.ResourceSourceConfiguration

interface ResourceLocatorEntryPoint {
    fun getResourceSourceConfigurations(variantTree: VariantTree): List<ResourceSourceConfiguration>
    fun onLocatorReady(variantTree: VariantTree, info: ResourceLocatorTaskInfo)
}