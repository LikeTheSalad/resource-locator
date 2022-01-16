package com.likethesalad.tools.resource.locator.android.extension.configuration.source

import com.likethesalad.tools.resource.collector.android.data.variant.VariantTree
import com.likethesalad.tools.resource.collector.filter.ResourceSourceFilterRule
import com.likethesalad.tools.resource.collector.source.ResourceSourceProvider
import java.io.File

abstract class ResourceSourceConfiguration(protected val variantTree: VariantTree) {

    protected val sourceFilterRules = mutableListOf<ResourceSourceFilterRule<*>>()
    fun addFilterRule(rule: ResourceSourceFilterRule<*>) {
        sourceFilterRules.add(rule)
    }

    abstract fun getSourceProviders(): List<ResourceSourceProvider>
    abstract fun getSourceFiles(): Iterable<File>
}