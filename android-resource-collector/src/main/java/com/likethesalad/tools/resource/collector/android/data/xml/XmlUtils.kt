package com.likethesalad.tools.resource.collector.android.data.xml

import com.likethesalad.tools.resource.collector.android.data.valuedir.ValueDir
import org.w3c.dom.Node
import java.io.File
import java.io.StringWriter
import javax.xml.transform.OutputKeys
import javax.xml.transform.TransformerException
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult

object XmlUtils {

    private val XML_FORMAT = Regex(".+\\.[xX][mM][lL]\$")
    private val OUTER_XML_TAGS_PATTERN = Regex("^<[^>]*>|<[^>]*>\$")
    private val contentExtractor by lazy {
        val transformer = TransformerFactory.newInstance().newTransformer()
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes")
        transformer
    }

    fun findXmlFiles(valueDir: ValueDir): List<File> {
        return valueDir.dir.listFiles { _, name ->
            isXmlFile(name)
        }?.toList() ?: emptyList()
    }

    private fun isXmlFile(name: String): Boolean {
        return XML_FORMAT.matches(name)
    }

    fun getContents(node: Node): String {
        val text = transformToText(node)
        return OUTER_XML_TAGS_PATTERN.replace(text, "")
    }

    private fun transformToText(doc: Node): String {
        val outText = StringWriter()
        val streamResult = StreamResult(outText)
        return try {
            contentExtractor.transform(DOMSource(doc), streamResult)
            outText.toString()
        } catch (e: TransformerException) {
            doc.textContent
        }
    }
}