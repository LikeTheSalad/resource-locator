package com.likethesalad.tools.resource.locator.android.collection

import com.likethesalad.tools.resource.api.Resource
import com.likethesalad.tools.resource.api.ResourceScope
import com.likethesalad.tools.resource.api.collection.ResourceCollection
import com.likethesalad.tools.resource.api.data.ResourceType

open class BaseResourceCollection(
    private val resources: List<Resource>,
    private val source: Any,
    private val scope: ResourceScope
) : ResourceCollection {

    override fun getAllResources(): List<Resource> = resources

    override fun getSource(): Any = source

    override fun getScope(): ResourceScope = scope

    override fun isEmpty(): Boolean = resources.isEmpty()

    override fun getResourcesByType(type: ResourceType): List<Resource> {
        return resources.filter { it.type() == type }
    }
}