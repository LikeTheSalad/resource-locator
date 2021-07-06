package com.likethesalad.tools.resource.extractor

import com.likethesalad.tools.resource.api.Resource
import com.likethesalad.tools.resource.api.collection.ResourceCollection

interface ResourceExtractor<T : Resource<out Any>> {
    fun extract(): ResourceCollection<T>
}