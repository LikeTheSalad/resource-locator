package com.likethesalad.tools.resourcelocator.api.utils

class AttributeContainer {

    private val map: MutableMap<String, String> = mutableMapOf()

    fun get(name: String): String? {
        return map[name]
    }

    fun set(name: String, value: String) {
        map[name] = value
    }

    fun remove(name: String) {
        map.remove(name)
    }
}