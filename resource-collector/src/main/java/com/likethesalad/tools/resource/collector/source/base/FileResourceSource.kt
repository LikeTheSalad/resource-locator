package com.likethesalad.tools.resource.collector.source.base

import com.likethesalad.tools.resource.collector.source.ResourceSource
import java.io.File

abstract class FileResourceSource(private val file: File) : ResourceSource {

    override fun getSource(): Any {
        return file
    }

    fun getFileSource() = getSource() as File
}
