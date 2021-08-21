package com.likethesalad.tools.resource.api.data

interface AttributeContainer {
    fun get(name: String): String?
    fun set(name: String, value: String)
    fun remove(name: String)
    fun asMap(): Map<String, String>
}