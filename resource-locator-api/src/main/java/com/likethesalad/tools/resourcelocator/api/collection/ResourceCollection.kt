package com.likethesalad.tools.resourcelocator.api.collection

import com.likethesalad.tools.resourcelocator.api.Resource
import com.likethesalad.tools.resourcelocator.api.ResourceScope

interface ResourceCollection<out T : Resource<out Any>> {
    fun getResources(): List<T>
    fun getSource(): Any
    fun getScope(): ResourceScope
}