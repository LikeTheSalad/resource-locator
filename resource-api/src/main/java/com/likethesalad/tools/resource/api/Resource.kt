package com.likethesalad.tools.resource.api

import com.likethesalad.tools.resource.api.data.AttributeContainer
import com.likethesalad.tools.resource.api.data.ResourceType

interface Resource {
    fun value(): Any
    fun attributes(): AttributeContainer
    fun scope(): ResourceScope
    fun type(): ResourceType
}