package com.likethesalad.tools.resourcelocator.android.common.xml

import com.likethesalad.tools.resourcelocator.android.common.xml.Constants.XML_RESOURCES_TAG
import org.w3c.dom.DOMException
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.Node
import org.w3c.dom.NodeList
import org.xml.sax.InputSource
import java.io.File
import java.io.StringReader
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.transform.OutputKeys
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult

class AndroidXmlResDocument(val document: Document) {

    val resources: Element = getOrCreateResources()

    fun saveToFile(file: File, indentSpaces: Int = 4) {
        val transformerFactory = TransformerFactory.newInstance()
        val transformer = transformerFactory.newTransformer()
        transformer.setOutputProperty(OutputKeys.INDENT, "yes")
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes")
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", indentSpaces.toString())
        val domSource = DOMSource(document)
        val streamResult = StreamResult(file)

        transformer.transform(domSource, streamResult)
    }

    fun append(item: Node) {
        try {
            resources.appendChild(item)
        } catch (e: DOMException) {
            resources.appendChild(document.importNode(item, true))
        }
    }

    fun appendAll(nodeList: NodeList) {
        for (it in 0 until nodeList.length) {
            append(nodeList.item(it))
        }
    }

    private fun getOrCreateResources(): Element {
        val resourcesList = document.getElementsByTagName(XML_RESOURCES_TAG)
        if (resourcesList.length > 0) {
            return resourcesList.item(0) as Element
        }
        val resources = document.createElement(XML_RESOURCES_TAG)
        document.appendChild(resources)
        return resources
    }

    class Factory(private val documentBuilderFactory: DocumentBuilderFactory) {

        fun fromFile(xmlFile: File): AndroidXmlResDocument {
            val dBuilder = documentBuilderFactory.newDocumentBuilder()
            val xmlInput = InputSource(StringReader(xmlFile.readText()))
            return AndroidXmlResDocument(
                dBuilder.parse(xmlInput)
            )
        }

        fun createNewDocument(): AndroidXmlResDocument {
            val document = documentBuilderFactory.newDocumentBuilder().newDocument().apply {
                xmlStandalone = true
            }
            return AndroidXmlResDocument(document)
        }
    }
}