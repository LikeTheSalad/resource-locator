package com.likethesalad.tools.resource.api.android.environment

import com.likethesalad.tools.resource.api.android.parser.SealedParseable
import com.likethesalad.tools.resource.api.android.parser.SealedParser

sealed class Language(val id: String) : SealedParseable {
    object Default : Language("main")
    class Custom(id: String) : Language(id)

    override fun hashCode(): Int {
        return id.hashCode()
    }

    override fun getSealedItemId(): String {
        return id
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

    companion object : SealedParser<Language>() {

        override fun createUnknown(id: String): Language {
            return Custom(id)
        }

        override fun getSealedObjects(): Set<Language> {
            return setOf(Default)
        }
    }
}