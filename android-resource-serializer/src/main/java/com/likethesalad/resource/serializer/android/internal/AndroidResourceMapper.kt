package com.likethesalad.resource.serializer.android.internal

import com.likethesalad.tools.resource.api.android.AndroidResource
import com.likethesalad.tools.resource.api.android.AndroidResourceScope
import com.likethesalad.tools.resource.api.android.BaseAndroidResource
import com.likethesalad.tools.resource.api.android.data.AndroidResourceType
import com.likethesalad.tools.resource.api.android.modules.integer.IntegerAndroidResource
import com.likethesalad.tools.resource.api.android.modules.string.StringAndroidResource
import com.likethesalad.tools.resource.api.data.ResourceType

internal object AndroidResourceMapper {

    fun mapToAndroidResource(structure: AndroidResourceJsonStructure): AndroidResource {
        val androidResourceType = AndroidResourceType.fromId(structure.type)
        val scope = AndroidResourceScope.fromName(structure.scope)
        val value = structure.value
        val attributes = structure.attributes

        return when (androidResourceType) {
            AndroidResourceType.StringType -> StringAndroidResource(attributes, value, scope)
            AndroidResourceType.IntegerType -> IntegerAndroidResource(attributes, value.toInt(), scope)
            else -> createUnknownAndroidResource(attributes, value, scope, androidResourceType)
        }
    }

    private fun createUnknownAndroidResource(
        attributes: Map<String, String>,
        value: Any, scope: AndroidResourceScope,
        type: AndroidResourceType
    ): AndroidResource {
        return object : BaseAndroidResource<Any>(attributes, value, scope) {
            override fun type(): ResourceType = type
        }
    }

    fun mapToJson(resource: AndroidResource): AndroidResourceJsonStructure {
        TODO()
    }
}