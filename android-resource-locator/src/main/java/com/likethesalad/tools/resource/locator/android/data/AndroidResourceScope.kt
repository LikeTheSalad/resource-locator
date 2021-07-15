package com.likethesalad.tools.resource.locator.android.data

import com.likethesalad.tools.resource.api.ResourceScope

data class AndroidResourceScope(
    val variantName: String,
    val language: String
) : ResourceScope {

    override fun getName(): String {
        TODO("Not yet implemented")
    }
}