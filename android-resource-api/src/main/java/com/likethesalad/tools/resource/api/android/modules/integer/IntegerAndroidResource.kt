package com.likethesalad.tools.resource.api.android.modules.integer

import com.likethesalad.tools.resource.api.Resource
import com.likethesalad.tools.resource.api.android.impl.AndroidResourceScope
import com.likethesalad.tools.resource.api.android.BaseAndroidResource
import com.likethesalad.tools.resource.api.android.impl.AndroidResourceType

class IntegerAndroidResource : BaseAndroidResource<Int> {

    constructor(attributes: Map<String, String>, value: Int, scope: AndroidResourceScope) : super(
        attributes,
        value,
        scope
    )

    constructor(name: String, value: Int, scope: AndroidResourceScope) : super(name, value, scope)

    override fun type(): Resource.Type = AndroidResourceType.IntegerType
}