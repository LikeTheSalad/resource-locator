package com.likethesalad.tools.resource.api.android.attributes.data

data class Namespace(val id: String, val value: String) {

    companion object {
        fun parse(value: String): Namespace {
            val firstEqualIndex = value.indexOfFirst { it == '=' }
            val id = value.substring(0, firstEqualIndex)
            val nsValue = value.substring(firstEqualIndex + 1)
            return Namespace(id, nsValue)
        }
    }

    fun asString(): String {
        return "$id=$value"
    }
}
