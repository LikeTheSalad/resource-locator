package com.likethesalad.tools.resource.locator.android.modules.collector.source

import com.google.auto.factory.AutoFactory
import com.google.auto.factory.Provided
import com.likethesalad.tools.resource.collector.source.ResourceSource
import com.likethesalad.tools.resource.locator.android.tools.xml.AndroidXmlResDocument
import java.io.File

@AutoFactory
class XmlResourceSource(
    private val xmlFile: File,
    @Provided private val documentFactory: AndroidXmlResDocument.Factory
) : ResourceSource {

    val document: AndroidXmlResDocument by lazy {
        documentFactory.fromFile(xmlFile)
    }

    override fun getSource(): Any = xmlFile
}