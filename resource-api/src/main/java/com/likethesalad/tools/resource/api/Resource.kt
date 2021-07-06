package com.likethesalad.tools.resource.api

import com.likethesalad.tools.resource.api.data.AttributeContainer
import com.likethesalad.tools.resource.api.data.Value

interface Resource<T : Any> {
    fun value(): Value<T>
    fun attributes(): AttributeContainer
    fun scope(): ResourceScope
}