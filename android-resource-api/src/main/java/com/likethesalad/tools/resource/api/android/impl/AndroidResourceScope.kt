package com.likethesalad.tools.resource.api.android.impl

import com.likethesalad.tools.resource.api.Resource
import com.likethesalad.tools.resource.api.android.environment.Language
import com.likethesalad.tools.resource.api.android.environment.Variant

data class AndroidResourceScope(
    val variant: Variant,
    val language: Language
) : Resource.Scope {

    companion object {
        private const val ITEM_VARIANT_POSITION = 0
        private const val ITEM_LANGUAGE_POSITION = 1
        private val stringFormat = Regex("^[^\\s\\t:]+:[^\\s\\t:]+\$")

        fun fromName(name: String): AndroidResourceScope {
            checkNameFormat(name)
            val items = name.split(":")
            val variant = Variant.fromId(items[ITEM_VARIANT_POSITION])
            val language = Language.fromId(items[ITEM_LANGUAGE_POSITION])

            return AndroidResourceScope(variant, language)
        }

        private fun checkNameFormat(name: String) {
            if (!stringFormat.matches(name)) {
                throw IllegalArgumentException(
                    "Invalid AndroidResourceScope format for: '$name'"
                )
            }
        }
    }

    override fun getName(): String {
        return "${variant.name}:${language.id}"
    }
}