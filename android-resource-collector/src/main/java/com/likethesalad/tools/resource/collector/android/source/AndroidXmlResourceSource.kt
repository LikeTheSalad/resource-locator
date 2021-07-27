package com.likethesalad.tools.resource.collector.android.source

import com.likethesalad.tools.resource.api.ResourceScope
import com.likethesalad.tools.resource.collector.android.data.AndroidXmlResDocument
import com.likethesalad.tools.resource.collector.source.ResourceSource
import java.io.File

class AndroidXmlResourceSource(
    private val xmlFile: File,
    private val scope: ResourceScope,
    private val documentFactory: AndroidXmlResDocument.Factory
) : ResourceSource {

    val document: AndroidXmlResDocument by lazy {
        documentFactory.fromFile(xmlFile)
    }

    override fun getSource(): Any = xmlFile

    override fun getScope(): ResourceScope = scope

    fun getFileSource() = getSource() as File
}