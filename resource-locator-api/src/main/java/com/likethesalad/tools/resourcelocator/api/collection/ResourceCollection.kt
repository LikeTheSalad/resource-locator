package com.likethesalad.tools.resourcelocator.api.collection

import com.likethesalad.tools.resourcelocator.api.Resource

interface ResourceCollection<out T : Resource<out Any>> {
    fun getResources(): List<T>
    fun getSource(): Any
    fun getScope(): ResourceCollectionScope
}