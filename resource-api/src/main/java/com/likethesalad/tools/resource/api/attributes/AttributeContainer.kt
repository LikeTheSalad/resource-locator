package com.likethesalad.tools.resource.api.attributes

interface AttributeContainer {
    fun get(key: AttributeKey): String?
    fun set(key: AttributeKey, value: String)
    fun remove(key: AttributeKey)
    fun asMap(): Map<AttributeKey, String>
}