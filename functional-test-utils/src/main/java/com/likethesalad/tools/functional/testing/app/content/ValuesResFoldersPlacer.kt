package com.likethesalad.tools.functional.testing.app.content

import com.likethesalad.tools.functional.testing.content.ProjectDirContentPlacer
import java.io.File

class ValuesResFoldersPlacer(
    private val flavorInputDir: File
) : ProjectDirContentPlacer() {

    override fun getRelativePath(): String = "src"

    @Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    override fun onDirReady(dir: File) {
        for (it in flavorInputDir.listFiles()) {
            it.copyRecursively(File(dir, it.name))
        }
    }
}