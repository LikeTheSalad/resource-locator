package com.likethesalad.tools.resource.locator.android.common

import com.likethesalad.tools.resource.api.data.ResourceType

sealed class AndroidResourceType(private val name: String) : ResourceType {
    object StringType : AndroidResourceType("string")

    override fun getName(): String = name
}