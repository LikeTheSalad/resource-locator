package com.likethesalad.tools.resource.api.attributes

import com.likethesalad.tools.resource.api.common.Named

interface AttributeKey : Named {
    fun type(): String
}