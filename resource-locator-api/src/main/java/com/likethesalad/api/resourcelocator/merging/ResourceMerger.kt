package com.likethesalad.api.resourcelocator.merging

import com.likethesalad.api.resourcelocator.Resource
import com.likethesalad.api.resourcelocator.ResourceCollection

interface ResourceMerger<T : ResourceCollection<out Resource<out Any>>> {
    fun merge(collections: List<T>): T
}