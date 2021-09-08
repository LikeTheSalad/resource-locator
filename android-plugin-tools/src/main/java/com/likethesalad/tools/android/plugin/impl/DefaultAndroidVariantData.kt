package com.likethesalad.tools.android.plugin.impl

import com.android.build.gradle.api.BaseVariant
import com.likethesalad.tools.android.plugin.AndroidVariantData
import org.gradle.api.artifacts.Configuration

class DefaultAndroidVariantData(private val variant: BaseVariant) : AndroidVariantData {

    override fun getVariantName(): String = variant.name

    override fun getVariantType(): String = variant.buildType.name

    override fun getVariantFlavors(): List<String> = variant.productFlavors.map { it.name }

    override fun getRuntimeConfiguration(): Configuration = variant.runtimeConfiguration
}