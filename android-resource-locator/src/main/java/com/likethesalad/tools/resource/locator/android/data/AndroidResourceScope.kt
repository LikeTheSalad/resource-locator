package com.likethesalad.tools.resource.locator.android.data

import com.likethesalad.tools.resource.api.ResourceScope
import com.likethesalad.tools.resource.locator.android.data.android.AndroidVariant
import com.likethesalad.tools.resource.locator.android.data.language.Language

data class AndroidResourceScope(
    val variant: AndroidVariant,
    val language: Language
) : ResourceScope {

    override fun getName(): String {
        TODO("Not yet implemented")
    }
}