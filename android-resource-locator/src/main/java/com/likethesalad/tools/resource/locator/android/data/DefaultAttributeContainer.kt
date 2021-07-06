package com.likethesalad.tools.resource.locator.android.data

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
}