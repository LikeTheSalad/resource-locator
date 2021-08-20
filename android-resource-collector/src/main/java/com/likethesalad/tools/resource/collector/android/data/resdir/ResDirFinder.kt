package com.likethesalad.tools.resource.collector.android.data.resdir

import com.likethesalad.tools.android.plugin.impl.DefaultAndroidExtension
import com.likethesalad.tools.resource.api.android.environment.Variant

class ResDirFinder(private val defaultAndroidExtension: DefaultAndroidExtension) {

    fun findResDirs(variant: Variant): List<ResDir> {
        return defaultAndroidExtension.getVariantSrcDirs(variant.name)
            .filter { it.exists() }
            .map { ResDir(variant, it) }
    }
}