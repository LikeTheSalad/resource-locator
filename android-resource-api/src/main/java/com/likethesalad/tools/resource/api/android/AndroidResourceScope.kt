package com.likethesalad.tools.resource.api.android

import com.likethesalad.tools.resource.api.ResourceScope
import com.likethesalad.tools.resource.api.android.data.AndroidVariant
import com.likethesalad.tools.resource.api.android.data.Language

data class AndroidResourceScope(
    val variant: AndroidVariant,
    val language: Language
) : ResourceScope {

    override fun getName(): String {
        TODO("Not yet implemented")
    }
}