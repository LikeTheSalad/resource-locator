package com.likethesalad.api.resourcelocator

import com.likethesalad.api.resourcelocator.common.Named

interface ResourceCollection<T : Resource<out Any>> : Named {
    fun getResources(): List<T>
    fun getSource(): Any
    fun getScope(): ResourceCollectionScope
}