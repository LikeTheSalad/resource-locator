package com.likethesalad.tools.resourcelocator.api.data

interface Value<T : Any> {
    fun get(): T
    fun set(value: T)
}