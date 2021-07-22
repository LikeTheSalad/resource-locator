package com.likethesalad.tools.resource.extractor

import com.likethesalad.tools.resource.api.collection.ResourceCollection

interface ResourceExtractor {
    fun extract(): ResourceCollection
    fun extract(source: ResourceSource): ResourceCollection
}