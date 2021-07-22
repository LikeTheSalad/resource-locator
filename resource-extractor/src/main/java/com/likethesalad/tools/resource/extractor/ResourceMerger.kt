package com.likethesalad.tools.resource.extractor

import com.likethesalad.tools.resource.api.collection.ResourceCollection

interface ResourceMerger {
    fun merge(collections: List<ResourceCollection>): ResourceCollection
}