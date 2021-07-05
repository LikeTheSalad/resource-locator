package com.likethesalad.tools.resourcelocator.api.extraction

import com.likethesalad.tools.resourcelocator.api.Resource
import com.likethesalad.tools.resourcelocator.api.collection.ResourceCollection

interface ResourceExtractor<T : Resource<out Any>> {
    fun extract(): ResourceCollection<T>
}