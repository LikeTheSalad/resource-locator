package com.likethesalad.tools.resource.locator.android.extension.configuration

import com.likethesalad.tools.resource.collector.filter.ResourceSourceFilterRule

class ResourceLocatorConfiguration {
    internal val filterRules = mutableListOf<ResourceSourceFilterRule<*>>()

    fun addSourceFilterRule(rule: ResourceSourceFilterRule<*>) {
        filterRules.add(rule)
    }
}