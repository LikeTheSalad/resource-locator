package com.likethesalad.tools.resource.api.data

interface Value<T : Any> {
    fun get(): T
    fun set(value: T)
}