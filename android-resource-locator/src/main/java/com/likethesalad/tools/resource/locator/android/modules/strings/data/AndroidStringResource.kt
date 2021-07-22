package com.likethesalad.tools.resource.locator.android.modules.strings.data

import com.likethesalad.tools.resource.api.data.ResourceType
import com.likethesalad.tools.resource.locator.android.data.AndroidResource
import com.likethesalad.tools.resource.locator.android.data.AndroidResourceScope
import com.likethesalad.tools.resource.locator.android.data.AndroidResourceType

class AndroidStringResource : AndroidResource<String> {

    constructor(attributes: Map<String, String>, value: String, scope: AndroidResourceScope) : super(
        attributes,
        value,
        scope
    )

    constructor(name: String, value: String, scope: AndroidResourceScope) : super(name, value, scope)

    fun getStringValue() = value() as String

    override fun type(): ResourceType = AndroidResourceType.StringType
}