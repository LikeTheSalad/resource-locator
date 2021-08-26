package com.likethesalad.tools.resource.collector.android.data.resdir

import com.likethesalad.tools.android.plugin.AndroidExtension
import com.likethesalad.tools.resource.api.android.environment.Variant

class ResDirFinder(private val androidExtension: AndroidExtension) {

    fun findResDirs(variant: Variant): List<ResDir> {
        return androidExtension.getVariantSrcDirs(variant.name)
            .filter { it.exists() }
            .map { ResDir(variant, it) }
    }
}