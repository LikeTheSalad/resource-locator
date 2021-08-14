package com.likethesalad.tools.resource.locator.android

import com.likethesalad.tools.resource.collector.ResourceCollector
import com.likethesalad.tools.resource.collector.android.data.variant.VariantTree

abstract class ResourceLocator {
    abstract fun getLocatorName(): String

    fun collectResources(variantTree: VariantTree) {
        val collector = getResourceCollector(variantTree)
        val resources = collector.collect()
    }

    abstract fun getResourceCollector(variantTree: VariantTree): ResourceCollector
}