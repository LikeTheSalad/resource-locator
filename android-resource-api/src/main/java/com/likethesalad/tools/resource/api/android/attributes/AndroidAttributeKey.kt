package com.likethesalad.tools.resource.api.android.attributes

import com.likethesalad.tools.resource.api.attributes.AttributeKey
import java.util.Objects

sealed class AndroidAttributeKey(
    private val value: String,
    private val type: String
) : AttributeKey {
    class Plain(value: String) : AndroidAttributeKey(value, ITEM_TYPE_PLAIN)
    class Namespaced(value: String, val namespaceValue: String) : AndroidAttributeKey(value, ITEM_TYPE_NAMESPACED) {
        override fun getExtraValue(): String = namespaceValue
    }

    companion object {
        private const val ITEM_TYPE_POSITION = 0
        private const val ITEM_VALUE_POSITION = 1
        private const val ITEM_EXTRA_POSITION = 2

        private const val ITEM_TYPE_PLAIN = "plain"
        private const val ITEM_TYPE_NAMESPACED = "namespaced"

        fun fromName(name: String): AndroidAttributeKey {
            val items = name.split("|")
            val type = items[ITEM_TYPE_POSITION]
            val value = items[ITEM_VALUE_POSITION]
            val extra = items[ITEM_EXTRA_POSITION]

            return when (type) {
                ITEM_TYPE_PLAIN -> Plain(value)
                ITEM_TYPE_NAMESPACED -> Namespaced(value, extra)
                else -> throw IllegalArgumentException("Item type $type not found")
            }
        }
    }

    override fun value(): String = value
    override fun getName(): String = "$type|$value|${getExtraValue()}"

    protected open fun getExtraValue(): String = ""

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is AndroidAttributeKey) return false

        if (value != other.value) return false
        if (type != other.type) return false
        if (getExtraValue() != other.getExtraValue()) return false

        return true
    }

    override fun hashCode(): Int {
        return Objects.hash(value, type, getExtraValue())
    }

    override fun toString(): String {
        return "AndroidAttributeKey(${getName()})"
    }
}