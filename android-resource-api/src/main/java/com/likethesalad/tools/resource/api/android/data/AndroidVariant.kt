package com.likethesalad.tools.resource.api.android.data

sealed class AndroidVariant(val name: String) {
    object Default : AndroidVariant("main")
    object Dependency : AndroidVariant("--dependency--")
    class Custom(name: String) : AndroidVariant(name)

    override fun hashCode(): Int {
        return name.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is AndroidVariant) return false

        if (name != other.name) return false

        return true
    }
}