package com.likethesalad.tools.testing

import java.io.File
import java.nio.file.Paths

object DummyResourcesFinder {

    private val packageResourcesPath: File by lazy {
        val resourceDirectory = Paths.get("src", "test", "dummy").toFile().absolutePath
        File(resourceDirectory)
    }

    fun getResourceFile(relativePath: String): File {
        return File(packageResourcesPath, relativePath)
    }
}