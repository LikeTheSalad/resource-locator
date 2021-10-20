package com.likethesalad.tools.resource.api.android.data

import com.likethesalad.tools.resource.api.android.utils.SealedParser
import com.likethesalad.tools.resource.api.data.ResourceType

sealed class AndroidResourceType(private val name: String) : ResourceType {
    object StringType : AndroidResourceType("string")
    object IntegerType : AndroidResourceType("integer")
    class Unknown(name: String) : AndroidResourceType(name)

    override fun getName(): String = name

    override fun toString(): String {
        return "AndroidResourceType(name='$name')"
    }

    companion object : SealedParser<AndroidResourceType>() {

        override fun createUnknown(id: String): AndroidResourceType {
            return Unknown(id)
        }

        override fun getInstanceId(item: AndroidResourceType): String {
            return item.getName()
        }

        override fun getSealedObjects(): Set<AndroidResourceType> {
            return setOf(StringType, IntegerType)
        }
    }
}