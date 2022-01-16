package com.likethesalad.tools.resource.collector.android.data.resdir

import com.likethesalad.tools.android.plugin.data.AndroidExtension
import com.likethesalad.tools.resource.api.android.environment.Variant
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ResDirFinder @Inject constructor(private val androidExtension: AndroidExtension) {

    fun findResDirs(variant: Variant): List<ResDir> {
        return androidExtension.getVariantSrcDirs(variant.name)
            .filter { it.exists() }
            .map { ResDir(variant, it) }
    }
}