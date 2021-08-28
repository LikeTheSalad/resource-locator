package com.likethesalad.tools.functional.testing.utils

import java.io.File
import java.nio.file.Paths

class TestAssetsProvider(
    private val testsDirName: String,
    private val assetsFolderName: String
) {

    private val functionalAssetsDir: File by lazy {
        Paths.get("src", testsDirName, "assets", assetsFolderName).toFile()
    }

    fun getAssetFile(relativePath: String): File {
        val asset = File(functionalAssetsDir, relativePath)
        if (!asset.exists()) {
            throw IllegalArgumentException("Asset '$asset' not found in '$assetsFolderName'")
        }

        return asset
    }
}