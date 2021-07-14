package com.likethesalad.tools.resource.locator.android.strings

import com.likethesalad.tools.resource.locator.android.AndroidResource
import com.likethesalad.tools.resource.locator.android.AndroidResourceScope

class AndroidStringResource : AndroidResource<String> {

    constructor(attributes: Map<String, String>, value: String, scope: AndroidResourceScope) : super(
        attributes,
        value,
        scope
    )

    constructor(name: String, value: String, scope: AndroidResourceScope) : super(name, value, scope)
}