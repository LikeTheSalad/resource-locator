package com.likethesalad.tools.resource.collector.android.data.resdir

import com.likethesalad.tools.android.build.AndroidExtensionHelper
import com.likethesalad.tools.resource.api.android.environment.Variant

class ResDirFinder(private val androidExtensionHelper: AndroidExtensionHelper) {

    fun findResDirs(variant: Variant): List<ResDir> {
        return androidExtensionHelper.getVariantSrcDirs(variant.name)
            .filter { it.exists() }
            .map { ResDir(variant, it) }
    }
}