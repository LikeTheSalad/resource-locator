package com.likethesalad.tools.resource.api.android.modules.string

import com.likethesalad.tools.resource.api.Resource
import com.likethesalad.tools.resource.api.android.BaseAndroidResource
import com.likethesalad.tools.resource.api.android.impl.AndroidResourceScope
import com.likethesalad.tools.resource.api.android.impl.AndroidResourceType
import com.likethesalad.tools.resource.api.attributes.AttributeKey

class StringAndroidResource : BaseAndroidResource<String> {

    constructor(attributes: Map<AttributeKey, String>, value: String, scope: AndroidResourceScope) : super(
        attributes,
        value,
        scope
    )

    constructor(name: String, value: String, scope: AndroidResourceScope) : super(name, value, scope)

    override fun type(): Resource.Type = AndroidResourceType.StringType

    fun stringValue() = value() as String
}