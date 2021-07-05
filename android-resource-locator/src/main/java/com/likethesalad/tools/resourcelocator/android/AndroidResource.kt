package com.likethesalad.tools.resourcelocator.android

import com.likethesalad.tools.resourcelocator.api.Resource
import com.likethesalad.tools.resourcelocator.api.ResourceScope
import com.likethesalad.tools.resourcelocator.api.utils.AttributeContainer

open class AndroidResource<T : Any>(
    val name: String,
    private var value: T,
    private val scope: AndroidResourceScope
) : Resource<T> {

    private val attributeContainer = AttributeContainer()

    companion object {
        private const val ATTR_NAME = "name"
    }

    init {
        attributeContainer.set(ATTR_NAME, name)
    }

    fun getAndroidScope(): AndroidResourceScope {
        return getScope() as AndroidResourceScope
    }

    override fun attributes(): AttributeContainer = attributeContainer

    override fun getValue(): T = value

    override fun setValue(value: T) {
        this.value = value
    }

    override fun getScope(): ResourceScope = scope
}