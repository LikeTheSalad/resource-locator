package com.likethesalad.tools.resource.api.android

import com.likethesalad.tools.resource.api.ResourceScope
import com.likethesalad.tools.resource.api.android.environment.Language
import com.likethesalad.tools.resource.api.android.environment.Variant

data class AndroidResourceScope(
    val variant: Variant,
    val language: Language
) : ResourceScope {

    companion object {
        fun fromName(name: String): AndroidResourceScope {
            TODO()
        }
    }

    override fun getName(): String {
        return "${variant.name}:${language.id}"
    }
}