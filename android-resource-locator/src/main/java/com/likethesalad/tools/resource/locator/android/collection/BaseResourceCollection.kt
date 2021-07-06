package com.likethesalad.tools.resource.locator.android.collection

import com.likethesalad.tools.resource.api.Resource
import com.likethesalad.tools.resource.api.ResourceScope
import com.likethesalad.tools.resource.api.collection.ResourceCollection

open class BaseResourceCollection<out T : Resource<out Any>>(
    private val source: Any,
    private val scope: ResourceScope
) : ResourceCollection<T> {

    private val resources = mutableListOf<T>()

    override fun getResources(): List<T> = resources

    override fun getSource(): Any = source

    override fun getScope(): ResourceScope = scope
}