package com.likethesalad.api.resourcelocator

import com.likethesalad.api.resourcelocator.common.Named

interface ResourceCollection<T : Any> : Named {
    fun getResources(): List<Resource<T>>
    fun getSource(): Any
    fun getScope(): ResourceCollectionScope
}