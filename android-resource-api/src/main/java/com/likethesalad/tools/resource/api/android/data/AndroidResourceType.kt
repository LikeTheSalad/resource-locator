package com.likethesalad.tools.resource.api.android.data

import com.likethesalad.tools.resource.api.data.ResourceType

sealed class AndroidResourceType(private val name: String) : ResourceType {
    object StringType : AndroidResourceType("string")
    object IntegerType : AndroidResourceType("integer")

    override fun getName(): String = name
}