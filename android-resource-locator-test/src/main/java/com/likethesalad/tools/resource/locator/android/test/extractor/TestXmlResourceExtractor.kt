package com.likethesalad.tools.resource.locator.android.test.extractor

import com.likethesalad.tools.resource.api.ResourceScope
import com.likethesalad.tools.resource.api.android.AndroidResourceScope
import com.likethesalad.tools.resource.api.android.modules.string.StringAndroidResource
import com.likethesalad.tools.resource.collector.android.data.AndroidXmlResDocument
import com.likethesalad.tools.resource.collector.android.extractor.XmlResourceExtractor
import org.w3c.dom.Node
import org.w3c.dom.NodeList

class TestXmlResourceExtractor : XmlResourceExtractor<StringAndroidResource>() {

    companion object {
        private const val XML_STRING_TAG = "string"
    }

    override fun getResourcesFromAndroidDocument(
        document: AndroidXmlResDocument,
        scope: ResourceScope
    ): List<StringAndroidResource> {
        return getStringAndroidResources(document, scope)
    }

    private fun getStringAndroidResources(
        document: AndroidXmlResDocument,
        scope: ResourceScope
    ): List<StringAndroidResource> {
        val stringList = mutableListOf<StringAndroidResource>()
        val nodeList = getStringNodeList(document)

        for (index in 0 until nodeList.length) {
            stringList.add(parseNodeToStringAndroidResource(nodeList.item(index), scope))
        }

        return stringList
    }

    private fun parseNodeToStringAndroidResource(node: Node, scope: ResourceScope): StringAndroidResource {
        val attributesMap = mutableMapOf<String, String>()
        val value = trimQuotes(node.textContent)
        val attributesNodes = node.attributes
        for (index in 0 until attributesNodes.length) {
            val attr = attributesNodes.item(index)
            attributesMap[attr.nodeName] = attr.textContent
        }
        return StringAndroidResource(attributesMap, value, scope as AndroidResourceScope)
    }

    private fun trimQuotes(text: String): String {
        return text.replace(Regex("^\"|\"$"), "")
    }

    private fun getStringNodeList(document: AndroidXmlResDocument): NodeList {
        return document.getElementsByTagName(XML_STRING_TAG)
    }
}