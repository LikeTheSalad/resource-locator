package com.likethesalad.tools.resource.collector.filter

import com.likethesalad.tools.resource.collector.source.ResourceSource

class ResourceSourceFilter {

    private val rules = mutableListOf<ResourceSourceFilterRule<*>>()

    fun exclude(source: ResourceSource): Boolean {
        rules.forEach {
            if (it.exclude(source)) {
                return true
            }
        }

        return false
    }

    fun addRule(rule: ResourceSourceFilterRule<*>) {
        if (rule in rules) {
            throw IllegalArgumentException("Rule already added: $rule")
        }

        rules.add(rule)
    }
}