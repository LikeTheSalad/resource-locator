package com.likethesalad.tools.resource.api.android.attributes

import com.likethesalad.tools.resource.api.attributes.AttributeKey

sealed class AndroidAttributeKey(val type: String) : AttributeKey {

    override fun getName(): String {
        TODO("Not yet implemented")
    }
}