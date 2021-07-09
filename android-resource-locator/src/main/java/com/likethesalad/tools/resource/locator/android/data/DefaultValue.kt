package com.likethesalad.tools.resource.locator.android.data

import com.likethesalad.tools.resource.api.data.Value

data class DefaultValue<T : Any>(private var value: T) : Value<T> {
    override fun get(): T = value

    override fun set(value: T) {
        this.value = value
    }
}