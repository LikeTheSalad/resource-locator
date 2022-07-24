package com.likethesalad.tools.resource.api

import com.likethesalad.tools.resource.api.common.Named
import com.likethesalad.tools.resource.api.attributes.AttributeContainer

interface Resource {
    fun value(): Any
    fun attributes(): AttributeContainer
    fun scope(): Scope
    fun type(): Type

    interface Type : Named
    interface Scope : Named
}