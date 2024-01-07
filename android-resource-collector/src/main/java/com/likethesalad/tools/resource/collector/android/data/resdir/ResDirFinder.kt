package com.likethesalad.tools.resource.collector.android.data.resdir

import com.likethesalad.tools.agpcompat.api.bridges.AndroidExtension
import com.likethesalad.tools.resource.api.android.environment.Variant

object ResDirFinder {

    fun findResDirs(androidExtension: AndroidExtension, variant: Variant): List<ResDir> {
        return androidExtension.getVariantSrcDirs(variant.name)
            .filter { it.exists() }
            .map { ResDir(variant, it) }
    }
}