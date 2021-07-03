package com.likethesalad.api.resourcelocator.extraction

import com.likethesalad.api.resourcelocator.ResourceCollection

interface ResourceExtractor<T : ResourceCollection<T>> {
    fun extract(): T
}