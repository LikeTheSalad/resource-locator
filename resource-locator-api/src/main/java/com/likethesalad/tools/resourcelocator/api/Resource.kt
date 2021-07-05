package com.likethesalad.tools.resourcelocator.api

import com.likethesalad.tools.resourcelocator.api.data.AttributeContainer
import com.likethesalad.tools.resourcelocator.api.data.Value

interface Resource<T : Any> {
    fun value(): Value<T>
    fun attributes(): AttributeContainer
    fun scope(): ResourceScope
}