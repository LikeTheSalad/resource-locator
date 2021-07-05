package com.likethesalad.tools.resourcelocator.api.data.impl

import com.likethesalad.tools.resourcelocator.api.data.Value

class DefaultValue<T : Any>(private var value: T) : Value<T> {
    override fun get(): T = value

    override fun set(value: T) {
        this.value = value
    }
}