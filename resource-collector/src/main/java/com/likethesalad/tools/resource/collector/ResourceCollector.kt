package com.likethesalad.tools.resource.collector

import com.likethesalad.tools.resource.api.collection.ResourceCollection
import com.likethesalad.tools.resource.collector.source.ResourceSourceContainer

abstract class ResourceCollector {

    fun collect(): ResourceCollection {
        val collections = mutableListOf<ResourceCollection>()
        val sourceProvider = getSourceProvider()
        for (source in sourceProvider.getSources()) {
            for (extractor in getExtractors()) {
                collections.add(extractor.extract(source))
            }
        }

        return getMerger().merge(collections)
    }

    abstract fun getSourceProvider(): ResourceSourceContainer

    abstract fun getExtractors(): List<ResourceExtractor>

    abstract fun getMerger(): ResourceMerger
}