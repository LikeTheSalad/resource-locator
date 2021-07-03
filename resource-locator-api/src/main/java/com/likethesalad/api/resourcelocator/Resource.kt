package com.likethesalad.api.resourcelocator

import com.likethesalad.api.resourcelocator.utils.AttributeContainer

interface Resource<T : Any> {
    fun getValue(): T
    fun setValue(value: T)
    fun attributes(): AttributeContainer
    fun getScope()
}