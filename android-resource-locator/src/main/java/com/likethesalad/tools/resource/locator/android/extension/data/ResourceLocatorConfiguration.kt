package com.likethesalad.tools.resource.locator.android.extension.data

import com.likethesalad.tools.resource.collector.android.data.variant.VariantTree
import com.likethesalad.tools.resource.collector.filter.ResourceSourceFilterRule
import com.likethesalad.tools.resource.collector.source.ResourceSourceProvider

interface ResourceLocatorConfiguration {
    fun getSourceFilterRules(variantTree: VariantTree): List<ResourceSourceFilterRule<*>>
    fun getSourceProviders(variantTree: VariantTree): List<ResourceSourceProvider>
}
