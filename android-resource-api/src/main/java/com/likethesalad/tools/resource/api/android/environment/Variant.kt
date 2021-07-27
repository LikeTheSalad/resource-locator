package com.likethesalad.tools.resource.api.android.environment

sealed class Variant(val name: String) {
    object Default : Variant("main")
    object Dependency : Variant("--dependency--")
    class Custom(name: String) : Variant(name)

    override fun hashCode(): Int {
        return name.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Variant) return false

        if (name != other.name) return false

        return true
    }
}