package com.likethesalad.tools.resource.extractor

import com.likethesalad.tools.resource.api.collection.ResourceCollection

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

    abstract fun getSourceProvider(): ResourceSourceProvider

    abstract fun getExtractors(): List<ResourceExtractor>

    abstract fun getMerger(): ResourceMerger
}