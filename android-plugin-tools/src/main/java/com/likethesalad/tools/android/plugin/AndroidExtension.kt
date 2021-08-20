package com.likethesalad.tools.android.plugin

import java.io.File

interface AndroidExtension {
    fun getVariantSrcDirs(variantName: String): Set<File>
    fun setVariantSrcDirs(variantName: String, dirs: Set<File>)
}