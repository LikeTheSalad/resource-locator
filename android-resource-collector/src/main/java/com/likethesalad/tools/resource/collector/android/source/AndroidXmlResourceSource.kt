package com.likethesalad.tools.resource.collector.android.source

import com.likethesalad.tools.resource.api.Resource
import com.likethesalad.tools.resource.collector.android.data.AndroidXmlResDocument
import com.likethesalad.tools.resource.collector.source.base.FileResourceSource
import java.io.File

class AndroidXmlResourceSource(
    private val xmlFile: File,
    private val scope: Resource.Scope,
) : FileResourceSource(xmlFile) {

    val document: AndroidXmlResDocument by lazy {
        AndroidXmlResDocument.fromFile(xmlFile)
    }

    override fun getScope(): Resource.Scope = scope
}