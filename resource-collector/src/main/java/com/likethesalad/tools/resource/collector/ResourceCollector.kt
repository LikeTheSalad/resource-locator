package com.likethesalad.tools.resource.collector

import com.likethesalad.tools.resource.api.collection.ResourceCollection
import com.likethesalad.tools.resource.collector.extractor.ResourceExtractor
import com.likethesalad.tools.resource.collector.extractor.ResourceExtractorProvider
import com.likethesalad.tools.resource.collector.filter.ResourceSourceFilter
import com.likethesalad.tools.resource.collector.merger.ResourceMerger
import com.likethesalad.tools.resource.collector.source.ResourceSource
import com.likethesalad.tools.resource.collector.source.ResourceSourceProvider

abstract class ResourceCollector {

    private val filter: ResourceSourceFilter by lazy { ResourceSourceFilter() }

    fun collect(): ResourceCollection {
        val collections = mutableListOf<ResourceCollection>()

        val sources = getSourceProvider().getSources()
        val extractors = getExtractorProvider().getExtractors()
        for (source in sources) {
            collections.addAll(getCollectionsFromSource(extractors, source))
        }

        return getMerger().merge(collections)
    }

    private fun getCollectionsFromSource(
        extractors: List<ResourceExtractor<ResourceSource>>,
        source: ResourceSource,
    ): List<ResourceCollection> {
        val collections = mutableListOf<ResourceCollection>()

        for (extractor in extractors) {
            extractor.extract(source)?.let { collection ->
                if (!collection.isEmpty()) {
                    collections.add(collection)
                }
            }
        }

        return collections
    }

    abstract fun getSourceProvider(): ResourceSourceProvider

    abstract fun getExtractorProvider(): ResourceExtractorProvider

    abstract fun getMerger(): ResourceMerger

    fun getSourceFilter(): ResourceSourceFilter {
        return filter
    }
}