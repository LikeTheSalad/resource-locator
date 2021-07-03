package com.likethesalad.tools.resourcelocator.api.extraction

import com.likethesalad.tools.resourcelocator.api.Resource
import com.likethesalad.tools.resourcelocator.api.ResourceCollection

interface ResourceExtractor<T : ResourceCollection<out Resource<out Any>>> {
    fun extract(): T
}