package com.likethesalad.tools.resource.locator.android.modules.strings.data

import com.likethesalad.tools.resource.api.android.AndroidResourceScope
import com.likethesalad.tools.resource.api.android.BaseAndroidResource
import com.likethesalad.tools.resource.api.android.data.AndroidResourceType
import com.likethesalad.tools.resource.api.data.ResourceType

class AndroidStringResource : BaseAndroidResource<String> {

    constructor(attributes: Map<String, String>, value: String, scope: AndroidResourceScope) : super(
        attributes,
        value,
        scope
    )

    constructor(name: String, value: String, scope: AndroidResourceScope) : super(name, value, scope)

    fun getStringValue() = value() as String

    override fun type(): ResourceType = AndroidResourceType.StringType
}