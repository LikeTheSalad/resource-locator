package com.likethesalad.tools.resource.collector

import com.likethesalad.tools.resource.api.collection.ResourceCollection
import com.likethesalad.tools.resource.collector.extractor.ResourceExtractorProvider
import com.likethesalad.tools.resource.collector.merger.ResourceMerger
import com.likethesalad.tools.resource.collector.source.ResourceSourceProvider

abstract class ResourceCollector {

    fun collect(): ResourceCollection {
        val collections = mutableListOf<ResourceCollection>()

        val sources = getSourceProvider().getSources()
        val extractors = getExtractorProvider().getExtractors()
        for (source in sources) {
            for (extractor in extractors) {
                collections.add(extractor.extract(source))
            }
        }

        return getMerger().merge(collections)
    }

    abstract fun getSourceProvider(): ResourceSourceProvider

    abstract fun getExtractorProvider(): ResourceExtractorProvider

    abstract fun getMerger(): ResourceMerger
}