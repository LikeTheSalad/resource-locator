package com.likethesalad.tools.resource.api.android.data

import com.likethesalad.tools.resource.api.data.AttributeContainer

class DefaultAttributeContainer : AttributeContainer {
    private val map: MutableMap<String, String> = mutableMapOf()

    override fun get(name: String): String? {
        return map[name]
    }

    override fun set(name: String, value: String) {
        map[name] = value
    }

    override fun remove(name: String) {
        map.remove(name)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is DefaultAttributeContainer) return false

        if (map != other.map) return false

        return true
    }

    override fun hashCode(): Int {
        return map.hashCode()
    }
}