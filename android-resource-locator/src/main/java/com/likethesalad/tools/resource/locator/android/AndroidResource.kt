package com.likethesalad.tools.resource.locator.android

import com.likethesalad.tools.resource.api.Resource
import com.likethesalad.tools.resource.api.ResourceScope
import com.likethesalad.tools.resource.api.data.AttributeContainer
import com.likethesalad.tools.resource.api.data.Value
import com.likethesalad.tools.resource.locator.android.data.DefaultAttributeContainer
import com.likethesalad.tools.resource.locator.android.data.DefaultValue
import java.util.Objects

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

    override fun hashCode(): Int {
        return Objects.hash(name, scope, attributeContainer, internalValue)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is AndroidResource<*>) return false

        if (name != other.name) return false
        if (internalValue != other.internalValue) return false
        if (scope != other.scope) return false
        if (attributeContainer != other.attributeContainer) return false

        return true
    }
}