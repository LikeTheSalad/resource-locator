package com.likethesalad.tools.android.plugin

interface AndroidVariantHelper {

    fun getVariantName(): String

    fun getVariantType(): String

    fun getVariantFlavors(): List<String>
}