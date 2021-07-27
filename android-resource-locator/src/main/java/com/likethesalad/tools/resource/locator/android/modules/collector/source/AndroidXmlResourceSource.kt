package com.likethesalad.tools.resource.locator.android.modules.collector.source

import com.google.auto.factory.AutoFactory
import com.google.auto.factory.Provided
import com.likethesalad.tools.resource.api.ResourceScope
import com.likethesalad.tools.resource.collector.source.ResourceSource
import com.likethesalad.tools.resource.locator.android.tools.xml.AndroidXmlResDocument
import java.io.File

@AutoFactory
class AndroidXmlResourceSource(
    private val xmlFile: File,
    private val scope: ResourceScope,
    @Provided private val documentFactory: AndroidXmlResDocument.Factory
) : ResourceSource {

    val document: AndroidXmlResDocument by lazy {
        documentFactory.fromFile(xmlFile)
    }

    override fun getSource(): Any = xmlFile

    override fun getScope(): ResourceScope = scope

    fun getFileSource() = getSource() as File
}