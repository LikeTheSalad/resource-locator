package com.likethesalad.tools.resource.locator.android

import com.likethesalad.tools.resource.api.Resource
import com.likethesalad.tools.resource.api.ResourceScope
import com.likethesalad.tools.resource.api.data.AttributeContainer
import com.likethesalad.tools.resource.api.data.Value
import com.likethesalad.tools.resource.api.data.impl.DefaultAttributeContainer
import com.likethesalad.tools.resource.api.data.impl.DefaultValue

open class AndroidResource<T : Any>(
    val name: String,
    value: T,
    private val scope: AndroidResourceScope
) : Resource<T> {

    private val attributeContainer = DefaultAttributeContainer()
    private val internalValue = DefaultValue(value)

    companion object {
        private const val ATTR_NAME = "name"
    }

    init {
        attributeContainer.set(ATTR_NAME, name)
    }

    fun getAndroidScope(): AndroidResourceScope {
        return scope() as AndroidResourceScope
    }

    override fun attributes(): AttributeContainer = attributeContainer

    override fun scope(): ResourceScope = scope

    override fun value(): Value<T> = internalValue
}