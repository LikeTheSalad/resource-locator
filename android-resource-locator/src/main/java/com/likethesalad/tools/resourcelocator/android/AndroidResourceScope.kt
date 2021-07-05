package com.likethesalad.tools.resourcelocator.android

import com.likethesalad.tools.resourcelocator.api.ResourceScope

data class AndroidResourceScope(
    val variantName: String,
    val language: String
) : ResourceScope {

    override fun getName(): String {
        TODO("Not yet implemented")
    }
}