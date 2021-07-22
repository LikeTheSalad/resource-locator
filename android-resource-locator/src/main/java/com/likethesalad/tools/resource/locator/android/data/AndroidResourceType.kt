package com.likethesalad.tools.resource.locator.android.data

import com.likethesalad.tools.resource.api.data.ResourceType

sealed class AndroidResourceType(private val name: String) : ResourceType {
    object StringType : AndroidResourceType("string")

    override fun getName(): String = name
}