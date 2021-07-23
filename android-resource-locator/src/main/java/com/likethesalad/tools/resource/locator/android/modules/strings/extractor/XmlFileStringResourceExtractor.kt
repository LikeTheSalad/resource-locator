package com.likethesalad.tools.resource.locator.android.modules.strings.extractor

import com.likethesalad.tools.resource.collector.extractor.ResourceExtractor
import com.likethesalad.tools.resource.locator.android.collection.FileResourceCollection
import com.likethesalad.tools.resource.locator.android.data.AndroidResourceScope
import com.likethesalad.tools.resource.locator.android.modules.strings.data.AndroidStringResource
import com.likethesalad.tools.resource.locator.android.tools.xml.AndroidXmlResDocument
import com.likethesalad.tools.resource.locator.android.tools.xml.Constants.XML_STRING_TAG
import org.w3c.dom.Node
import org.w3c.dom.NodeList
import java.io.File

class XmlFileStringResourceExtractor(
    private val xmlFile: File,
    private val scope: AndroidResourceScope,
    private val xmlDocumentFactory: AndroidXmlResDocument.Factory
) : ResourceExtractor {

    override fun extract(): FileResourceCollection {
        val document = xmlDocumentFactory.fromFile(xmlFile)
        val resources = getAndroidStringResources(document)

        return FileResourceCollection(resources, xmlFile, scope)
    }

    private fun getAndroidStringResources(document: AndroidXmlResDocument): List<AndroidStringResource> {
        val stringList = mutableListOf<AndroidStringResource>()
        val nodeList = getStringNodeList(document)

        for (index in 0 until nodeList.length) {
            stringList.add(parseNodeToAndroidStringResource(nodeList.item(index)))
        }

        return stringList
    }

    private fun parseNodeToAndroidStringResource(node: Node): AndroidStringResource {
        val attributesMap = mutableMapOf<String, String>()
        val value = trimQuotes(node.textContent)
        val attributesNodes = node.attributes
        for (index in 0 until attributesNodes.length) {
            val attr = attributesNodes.item(index)
            attributesMap[attr.nodeName] = attr.textContent
        }
        return AndroidStringResource(attributesMap, value, scope)
    }

    private fun trimQuotes(text: String): String {
        return text.replace(Regex("^\"|\"$"), "")
    }

    private fun getStringNodeList(document: AndroidXmlResDocument): NodeList {
        return document.getElementsByTagName(XML_STRING_TAG)
    }
}