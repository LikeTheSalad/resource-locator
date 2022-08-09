package com.likethesalad.tools.resource.api.collection

import com.likethesalad.tools.resource.api.Resource

open class BasicResourceCollection(
    private val resources: List<Resource>
) : ResourceCollection {

    override fun getAllResources(): List<Resource> = resources

    override fun isEmpty(): Boolean = resources.isEmpty()

    override fun getResourcesByType(type: Resource.Type): List<Resource> {
        return resources.filter { it.type() == type }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ResourceCollection) return false

        if (resources != other.getAllResources()) return false

        return true
    }

    override fun hashCode(): Int {
        return resources.hashCode()
    }

    override fun toString(): String {
        return "BasicResourceCollection(resources=$resources)"
    }
}