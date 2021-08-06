package com.likethesalad.tools.resource.collector.android.helpers

interface AndroidVariantHelper {

    fun getVariantName(): String

    fun getVariantType(): String

    fun getVariantFlavors(): List<String>
}