package com.likethesalad.tools.resourcelocator.api

import com.likethesalad.tools.resourcelocator.api.common.Named

interface ResourceCollection<T : Resource<out Any>> : Named {
    fun getResources(): List<T>
    fun getSource(): Any
    fun getScope(): ResourceCollectionScope
}