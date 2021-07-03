package com.likethesalad.tools.resourcelocator.api

import com.likethesalad.tools.resourcelocator.api.utils.AttributeContainer

interface Resource<T : Any> {
    fun getValue(): T
    fun setValue(value: T)
    fun attributes(): AttributeContainer
    fun getScope()
}