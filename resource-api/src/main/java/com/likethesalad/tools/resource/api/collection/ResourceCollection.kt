package com.likethesalad.tools.resource.api.collection

import com.likethesalad.tools.resource.api.Resource
import com.likethesalad.tools.resource.api.ResourceScope

interface ResourceCollection<out T : Resource<out Any>> {
    fun getResources(): List<T>
    fun getSource(): Any
    fun getScope(): ResourceScope
}