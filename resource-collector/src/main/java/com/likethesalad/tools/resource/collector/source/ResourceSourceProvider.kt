package com.likethesalad.tools.resource.collector.source

import com.likethesalad.tools.resource.collector.filter.ResourceSourceFilter
import com.likethesalad.tools.resource.collector.filter.ResourceSourceFilterRule

abstract class ResourceSourceProvider {
    private val filter: ResourceSourceFilter by lazy { ResourceSourceFilter() }

    protected abstract fun doGetSources(): List<ResourceSource>

    fun getSources(): List<ResourceSource> {
        return doGetSources().filter { !filter.exclude(it) }
    }

    fun addFilterRules(rules: List<ResourceSourceFilterRule<*>>) {
        for (rule in rules) {
            filter.addRule(rule)
        }
    }
}