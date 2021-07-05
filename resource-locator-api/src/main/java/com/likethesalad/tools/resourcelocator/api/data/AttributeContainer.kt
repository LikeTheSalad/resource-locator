package com.likethesalad.tools.resourcelocator.api.data

interface AttributeContainer {
    fun get(name: String): String?
    fun set(name: String, value: String)
    fun remove(name: String)
}