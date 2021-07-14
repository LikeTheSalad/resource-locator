package com.likethesalad.tools.resource.locator.android.strings.extractor

import com.google.common.truth.Truth
import com.likethesalad.tools.resource.locator.android.AndroidResourceScope
import com.likethesalad.tools.resource.locator.android.common.xml.AndroidXmlResDocument
import com.likethesalad.tools.resource.locator.android.strings.AndroidStringResource
import com.likethesalad.tools.resource.locator.android.testutils.DummyResourcesFinder
import org.junit.Test
import java.io.File
import javax.xml.parsers.DocumentBuilderFactory

class XmlFileStringResourceExtractorTest {

    private val scope = AndroidResourceScope("someVariant", "es")
    private val xmlDocumentFactory = AndroidXmlResDocument.Factory(DocumentBuilderFactory.newInstance())

    @Test
    fun `Get all string resources from file`() {
        val file = getStringsXmlFile("strings_1.xml")
        val instance = createInstance(file)

        val result = instance.extract()

        Truth.assertThat(result.getFileSource()).isEqualTo(file)
        Truth.assertThat(result.getScope()).isEqualTo(scope)
        val resources = result.getResources()
        Truth.assertThat(resources.size).isEqualTo(2)
        Truth.assertThat(resources).containsExactly(
            createStringResource("welcome_1", "The welcome message for TesT1"),
            createStringResource(
                "message_non_translatable_1", "Non translatable TesT1",
                mapOf("translatable" to "false")
            )
        )
    }

    private fun createStringResource(
        name: String,
        value: String,
        attributes: Map<String, String> = emptyMap()
    ): AndroidStringResource {
        val resource = AndroidStringResource(name, value, scope)
        for ((key, attr) in attributes) {
            resource.attributes().set(key, attr)
        }
        return resource
    }

    private fun createInstance(xmlFile: File): XmlFileStringResourceExtractor {
        return XmlFileStringResourceExtractor(xmlFile, scope, xmlDocumentFactory)
    }

    private fun getStringsXmlFile(name: String): File {
        return DummyResourcesFinder.getResourceFile("resources/strings/$name")
    }
}