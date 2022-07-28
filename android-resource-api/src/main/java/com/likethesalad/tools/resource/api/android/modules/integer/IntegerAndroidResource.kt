package com.likethesalad.tools.resource.api.android.modules.integer

import com.likethesalad.tools.resource.api.Resource
import com.likethesalad.tools.resource.api.android.BaseAndroidResource
import com.likethesalad.tools.resource.api.android.impl.AndroidResourceScope
import com.likethesalad.tools.resource.api.android.impl.AndroidResourceType
import com.likethesalad.tools.resource.api.attributes.AttributeKey

class IntegerAndroidResource : BaseAndroidResource<Int> {

    constructor(attributes: Map<AttributeKey, String>, value: Int, scope: AndroidResourceScope) : super(
        attributes,
        value,
        scope
    )

    constructor(name: String, value: Int, scope: AndroidResourceScope) : super(name, value, scope)

    override fun type(): Resource.Type = AndroidResourceType.IntegerType
}