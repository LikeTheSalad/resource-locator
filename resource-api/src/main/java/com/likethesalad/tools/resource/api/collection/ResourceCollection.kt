package com.likethesalad.tools.resource.api.collection

import com.likethesalad.tools.resource.api.Resource

interface ResourceCollection {
    fun getAllResources(): List<Resource>
    fun getResourcesByType(type: Resource.Type): List<Resource>
    fun isEmpty(): Boolean
}