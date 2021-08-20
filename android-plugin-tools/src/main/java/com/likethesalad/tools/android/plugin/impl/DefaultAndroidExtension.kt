package com.likethesalad.tools.android.plugin.impl

import com.android.build.gradle.AppExtension
import com.android.build.gradle.api.AndroidSourceDirectorySet
import com.likethesalad.tools.android.plugin.AndroidExtension
import java.io.File

class DefaultAndroidExtension(private val extension: AppExtension) : AndroidExtension {

    override fun getVariantSrcDirs(variantName: String): Set<File> {
        return getVariantRes(variantName).srcDirs
    }

    override fun setVariantSrcDirs(variantName: String, dirs: Set<File>) {
        getVariantRes(variantName).setSrcDirs(dirs)
    }

    private fun getVariantRes(variantName: String): AndroidSourceDirectorySet {
        return extension.sourceSets.getByName(variantName).res
    }
}