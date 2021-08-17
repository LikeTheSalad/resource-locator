package com.likethesalad.tools.resource.api.collection

import com.likethesalad.tools.resource.api.Resource
import com.likethesalad.tools.resource.api.data.ResourceType

interface ResourceCollection {
    fun getAllResources(): List<Resource>
    fun getResourcesByType(type: ResourceType): List<Resource>
    fun isEmpty(): Boolean
}