package com.likethesalad.tools.resource.api.android.data

sealed class Language(val id: String) {
    object Default : Language("main")
    class Custom(id: String) : Language(id)
}