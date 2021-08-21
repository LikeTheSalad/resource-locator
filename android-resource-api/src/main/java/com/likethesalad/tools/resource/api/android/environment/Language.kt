package com.likethesalad.tools.resource.api.android.environment

import com.likethesalad.tools.resource.api.android.utils.SealedParser

sealed class Language(val id: String) {
    object Default : Language("main")
    class Custom(id: String) : Language(id)

    override fun hashCode(): Int {
        return id.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Language) return false

        if (id != other.id) return false

        return true
    }

    override fun toString(): String {
        return "Language(id='$id')"
    }

    companion object : SealedParser<Language>(Language::class) {

        override fun createUnknown(id: String): Language {
            return Custom(id)
        }

        override fun getId(item: Language): String {
            return item.id
        }

    }
}