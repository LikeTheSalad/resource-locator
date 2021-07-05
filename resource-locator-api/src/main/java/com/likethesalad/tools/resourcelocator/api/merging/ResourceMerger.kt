package com.likethesalad.tools.resourcelocator.api.merging

import com.likethesalad.tools.resourcelocator.api.Resource
import com.likethesalad.tools.resourcelocator.api.collection.ResourceCollection

interface ResourceMerger<T : ResourceCollection<out Resource<out Any>>> {
    fun merge(collections: List<T>): T
}