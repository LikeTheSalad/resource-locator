package com.likethesalad.tools.resource.collector.extractor

import com.likethesalad.tools.resource.api.collection.ResourceCollection
import com.likethesalad.tools.resource.collector.source.ResourceSource

abstract class ResourceExtractor<T : ResourceSource> {

    protected abstract fun doExtract(source: T): ResourceCollection

    @Suppress("UNCHECKED_CAST")
    fun extract(source: ResourceSource): ResourceCollection? {
        return try {
            doExtract(source as T)
        } catch (e: ClassCastException) {
            null
        }
    }
}