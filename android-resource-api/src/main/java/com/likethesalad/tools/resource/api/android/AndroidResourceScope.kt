package com.likethesalad.tools.resource.api.android

import com.likethesalad.tools.resource.api.ResourceScope
import com.likethesalad.tools.resource.api.android.environment.Language
import com.likethesalad.tools.resource.api.android.environment.Variant

data class AndroidResourceScope(
    val variant: Variant,
    val language: Language
) : ResourceScope {

    override fun getName(): String {
        TODO("Not yet implemented")
    }
}