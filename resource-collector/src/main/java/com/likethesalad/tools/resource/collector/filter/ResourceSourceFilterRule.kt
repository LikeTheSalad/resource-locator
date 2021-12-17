package com.likethesalad.tools.resource.collector.filter

import com.likethesalad.tools.resource.collector.source.ResourceSource

abstract class ResourceSourceFilterRule<T : ResourceSource> {

    protected abstract fun doExclude(source: ResourceSource): Boolean

    @Suppress("UNCHECKED_CAST")
    fun exclude(source: ResourceSource): Boolean {
        return try {
            doExclude(source as T)
        } catch (e: ClassCastException) {
            false
        }
    }
}
