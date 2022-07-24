package com.likethesalad.tools.resource.api.android.data

import com.likethesalad.tools.resource.api.data.AttributeContainer
import com.likethesalad.tools.resource.api.data.AttributeKey

internal class DefaultAttributeContainer(base: Map<AttributeKey, String> = emptyMap()) : AttributeContainer {
    private val map: MutableMap<AttributeKey, String> = mutableMapOf()

    init {
        map.putAll(base)
    }

    override fun get(key: AttributeKey): String? {
        return map[key]
    }

    override fun set(key: AttributeKey, value: String) {
        map[key] = value
    }

    override fun remove(key: AttributeKey) {
        map.remove(key)
    }

    override fun asMap(): Map<String, String> {
        return map.mapKeys { it.key.getName() }
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

    override fun toString(): String {
        return "DefaultAttributeContainer(map=$map)"
    }
}