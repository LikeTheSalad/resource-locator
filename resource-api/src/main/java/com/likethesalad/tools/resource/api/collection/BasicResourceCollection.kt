package com.likethesalad.tools.resource.api.collection

import com.likethesalad.tools.resource.api.Resource
import com.likethesalad.tools.resource.api.data.ResourceType

open class BasicResourceCollection(
    private val resources: List<Resource>
) : ResourceCollection {

    override fun getAllResources(): List<Resource> = resources

    override fun isEmpty(): Boolean = resources.isEmpty()

    override fun getResourcesByType(type: ResourceType): List<Resource> {
        return resources.filter { it.type() == type }
    }
}