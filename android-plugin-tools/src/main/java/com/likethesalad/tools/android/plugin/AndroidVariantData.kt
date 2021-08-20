package com.likethesalad.tools.android.plugin

interface AndroidVariantData {
    fun getVariantName(): String
    fun getVariantType(): String
    fun getVariantFlavors(): List<String>
}