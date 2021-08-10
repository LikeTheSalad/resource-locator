package com.likethesalad.tools.resource.api.android

import com.likethesalad.tools.resource.api.ResourceScope
import com.likethesalad.tools.resource.api.android.data.DefaultAttributeContainer
import com.likethesalad.tools.resource.api.data.AttributeContainer
import java.util.Objects

abstract class BaseAndroidResource<T : Any>(
    attributes: Map<String, String>,
    private val value: T,
    private val scope: AndroidResourceScope
) : AndroidResource {

    private val name: String
    private val attributeContainer = DefaultAttributeContainer()

    companion object {
        private const val ATTR_NAME = "name"
    }

    init {
        for ((key, attribute) in attributes) {
            attributeContainer.set(key, attribute)
        }
        name = attributeContainer.get(ATTR_NAME)!!
    }

    constructor(name: String, value: T, scope: AndroidResourceScope)
            : this(mapOf(ATTR_NAME to name), value, scope)

    fun getAndroidScope(): AndroidResourceScope {
        return scope() as AndroidResourceScope
    }

    override fun attributes(): AttributeContainer = attributeContainer

    override fun scope(): ResourceScope = scope

    override fun value(): Any = value

    override fun name(): String = name

    override fun hashCode(): Int {
        return Objects.hash(name, scope, attributeContainer, value)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is AndroidResource) return false

        if (type() != other.type()) return false
        if (name != other.name()) return false
        if (value != other.value()) return false
        if (scope != other.scope()) return false
        if (attributeContainer != other.attributes()) return false

        return true
    }
}