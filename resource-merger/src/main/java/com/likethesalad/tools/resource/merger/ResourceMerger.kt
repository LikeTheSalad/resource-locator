package com.likethesalad.tools.resource.merger

import com.likethesalad.tools.resource.api.Resource
import com.likethesalad.tools.resource.api.collection.ResourceCollection

interface ResourceMerger<T : ResourceCollection<Resource<out Any>>> {
    fun merge(collections: List<T>): T
}