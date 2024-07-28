package com.likethesalad.android.string.resources.locator.extractor

import com.likethesalad.tools.resource.api.Resource
import com.likethesalad.tools.resource.api.android.attributes.namespaced
import com.likethesalad.tools.resource.api.android.attributes.plain
import com.likethesalad.tools.resource.api.android.impl.AndroidResourceScope
import com.likethesalad.tools.resource.api.android.modules.string.StringAndroidResource
import com.likethesalad.tools.resource.api.attributes.AttributeKey
import com.likethesalad.tools.resource.collector.android.data.AndroidXmlResDocument
import com.likethesalad.tools.resource.collector.android.data.xml.XmlUtils
import com.likethesalad.tools.resource.collector.android.extractor.XmlResourceExtractor
import org.w3c.dom.Node
import org.w3c.dom.NodeList

class StringXmlResourceExtractor : XmlResourceExtractor<StringAndroidResource>() {

    companion object {
        private const val STRING_RESOURCE_PATH = "/resources/string"
    }

    override fun getResourcesFromAndroidDocument(
        document: AndroidXmlResDocument,
        scope: Resource.Scope
    ): List<StringAndroidResource> {
        return getStringAndroidResources(document, scope)
    }

    private fun getStringAndroidResources(
        document: AndroidXmlResDocument,
        scope: Resource.Scope
    ): List<StringAndroidResource> {
        val stringList = mutableListOf<StringAndroidResource>()
        val nodeList = getStringNodeList(document)

        for (index in 0 until nodeList.length) {
            stringList.add(parseNodeToStringAndroidResource(nodeList.item(index), scope))
        }

        return stringList
    }

    private fun parseNodeToStringAndroidResource(node: Node, scope: Resource.Scope): StringAndroidResource {
        val attributesMap = mutableMapOf<AttributeKey, String>()
        val value = trimQuotes(getNodeText(node))
        val attributesNodes = node.attributes
        for (index in 0 until attributesNodes.length) {
            val attr = attributesNodes.item(index)
            val attrName = attr.localName
            val key = attr.namespaceURI?.let { namespaceValue ->
                namespaced(attrName, namespaceValue)
            } ?: run {
                plain(attrName)
            }
            attributesMap[key] = attr.textContent
        }
        return StringAndroidResource(attributesMap, value, scope as AndroidResourceScope)
    }

    private fun getNodeText(node: Node): String {
        return XmlUtils.getContents(node)
    }

    private fun trimQuotes(text: String): String {
        return text.replace(Regex("(?<!\\\\)\""), "")
    }

    private fun getStringNodeList(document: AndroidXmlResDocument): NodeList {
        return document.getElementsByXPath(STRING_RESOURCE_PATH)
    }
}