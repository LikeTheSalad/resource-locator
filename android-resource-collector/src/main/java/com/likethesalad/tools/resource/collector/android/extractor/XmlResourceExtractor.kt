package com.likethesalad.tools.resource.collector.android.extractor

import com.likethesalad.tools.resource.api.android.AndroidResource
import com.likethesalad.tools.resource.api.collection.ResourceCollection
import com.likethesalad.tools.resource.collector.android.collection.FileResourceCollection
import com.likethesalad.tools.resource.collector.android.data.AndroidXmlResDocument
import com.likethesalad.tools.resource.collector.android.source.AndroidXmlResourceSource
import com.likethesalad.tools.resource.collector.extractor.ResourceExtractor
import org.w3c.dom.Node
import org.w3c.dom.NodeList

abstract class XmlResourceExtractor<T : AndroidResource> : ResourceExtractor<AndroidXmlResourceSource>() {

    override fun doExtract(source: AndroidXmlResourceSource): ResourceCollection {
        val resources = getResourcesFromAndroidDocument(source.document)

        return FileResourceCollection(resources, source.getFileSource(), source.getScope())
    }

    abstract fun getResourcesFromAndroidDocument(document: AndroidXmlResDocument): List<T>

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