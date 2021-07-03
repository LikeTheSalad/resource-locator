package com.likethesalad.api.resourcelocator.merging

import com.likethesalad.api.resourcelocator.ResourceCollection

interface ResourceMerger<T : ResourceCollection<T>> {
    fun merge(collections: List<T>): ResourceCollection<T>
}