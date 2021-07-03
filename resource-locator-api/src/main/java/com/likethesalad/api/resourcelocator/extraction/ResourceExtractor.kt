package com.likethesalad.api.resourcelocator.extraction

import com.likethesalad.api.resourcelocator.Resource
import com.likethesalad.api.resourcelocator.ResourceCollection

interface ResourceExtractor<T : ResourceCollection<Resource<out Any>>> {
    fun extract(): T
}