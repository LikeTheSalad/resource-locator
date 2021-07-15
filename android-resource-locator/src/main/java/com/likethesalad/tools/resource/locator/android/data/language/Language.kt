package com.likethesalad.tools.resource.locator.android.data.language

sealed class Language(val id: String) {
    object Default : Language("main")
    class Custom(id: String) : Language(id)
}