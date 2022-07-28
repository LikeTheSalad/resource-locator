package com.likethesalad.resource.serializer.android.internal

import com.likethesalad.tools.resource.api.Resource
import com.likethesalad.tools.resource.api.android.AndroidResource
import com.likethesalad.tools.resource.api.android.BaseAndroidResource
import com.likethesalad.tools.resource.api.android.attributes.AndroidAttributeKey
import com.likethesalad.tools.resource.api.android.impl.AndroidResourceScope
import com.likethesalad.tools.resource.api.android.impl.AndroidResourceType
import com.likethesalad.tools.resource.api.android.modules.integer.IntegerAndroidResource
import com.likethesalad.tools.resource.api.android.modules.string.StringAndroidResource
import com.likethesalad.tools.resource.api.attributes.AttributeKey

internal object AndroidResourceMapper {

    fun mapToAndroidResource(structure: AndroidResourceJsonStructure): AndroidResource {
        val androidResourceType = AndroidResourceType.fromId(structure.type)
        val scope = AndroidResourceScope.fromName(structure.scope)
        val value = structure.value
        val attributes: Map<AttributeKey, String> =
            structure.attributes.mapKeys { AndroidAttributeKey.fromName(it.key) }

        return when (androidResourceType) {
            AndroidResourceType.StringType -> StringAndroidResource(attributes, value, scope)
            AndroidResourceType.IntegerType -> IntegerAndroidResource(attributes, value.toInt(), scope)
            else -> createUnknownAndroidResource(attributes, value, scope, androidResourceType)
        }
    }

    private fun createUnknownAndroidResource(
        attributes: Map<AttributeKey, String>,
        value: Any, scope: AndroidResourceScope,
        type: AndroidResourceType
    ): AndroidResource {
        return object : BaseAndroidResource<Any>(attributes, value, scope) {
            override fun type(): Resource.Type = type
        }
    }

    fun mapToJson(resource: AndroidResource): AndroidResourceJsonStructure {
        return AndroidResourceJsonStructure(
            resource.attributes().asMap(),
            resource.value().toString(),
            resource.scope().getName(),
            resource.type().getName()
        )
    }
}