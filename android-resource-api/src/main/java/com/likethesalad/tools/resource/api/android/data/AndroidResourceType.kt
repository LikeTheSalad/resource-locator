package com.likethesalad.tools.resource.api.android.data

import com.likethesalad.tools.resource.api.data.ResourceType

sealed class AndroidResourceType(private val name: String) : ResourceType {
    object StringType : AndroidResourceType("string")
    object IntegerType : AndroidResourceType("integer")
    class Unknown(name: String) : AndroidResourceType(name)

    override fun getName(): String = name

    override fun toString(): String {
        return "AndroidResourceType(name='$name')"
    }
}