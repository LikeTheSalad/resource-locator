package com.likethesalad.tools.resource.api.android.environment

import com.likethesalad.tools.resource.api.android.parser.SealedParseable
import com.likethesalad.tools.resource.api.android.parser.SealedParser

sealed class Variant(val name: String) : SealedParseable {
    object Default : Variant("main")
    object Dependency : Variant("--dependency--")
    class Custom(name: String) : Variant(name)

    override fun getSealedItemId(): String {
        return name
    }

    override fun hashCode(): Int {
        return name.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Variant) return false

        if (name != other.name) return false

        return true
    }

    companion object : SealedParser<Variant>() {
        override fun createUnknown(id: String): Variant {
            return Custom(id)
        }

        override fun getSealedObjects(): Set<Variant> {
            return setOf(Default, Dependency)
        }
    }
}