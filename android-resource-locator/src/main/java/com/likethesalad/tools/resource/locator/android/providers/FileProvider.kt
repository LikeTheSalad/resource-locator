package com.likethesalad.tools.resource.locator.android.providers

import java.io.File

interface FileProvider {
    fun getFile(): File
}