package com.likethesalad.tools.resource.api.android.data

import com.likethesalad.tools.resource.api.android.parser.SealedParseable
import com.likethesalad.tools.resource.api.android.parser.SealedParser
import com.likethesalad.tools.resource.api.data.ResourceType

sealed class AndroidResourceType(private val name: String) : ResourceType, SealedParseable {
    object StringType : AndroidResourceType("string")
    object IntegerType : AndroidResourceType("integer")
    class Unknown(name: String) : AndroidResourceType(name)

    override fun getName(): String = name

    override fun toString(): String {
        return "AndroidResourceType(name='$name')"
    }

    override fun getSealedItemId(): String {
        return name
    }

    companion object : SealedParser<AndroidResourceType>() {

        override fun createUnknown(id: String): AndroidResourceType {
            return Unknown(id)
        }

        override fun getSealedObjects(): Set<AndroidResourceType> {
            return setOf(StringType, IntegerType)
        }
    }
}