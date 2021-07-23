package com.likethesalad.tools.resource.collector

import com.likethesalad.tools.resource.api.collection.ResourceCollection

interface ResourceMerger {
    fun merge(collections: List<ResourceCollection>): ResourceCollection
}