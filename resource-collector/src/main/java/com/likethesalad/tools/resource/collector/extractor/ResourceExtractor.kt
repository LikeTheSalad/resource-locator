package com.likethesalad.tools.resource.collector.extractor

import com.likethesalad.tools.resource.api.collection.ResourceCollection
import com.likethesalad.tools.resource.collector.source.ResourceSource

interface ResourceExtractor {
    fun extract(source: ResourceSource): ResourceCollection
}