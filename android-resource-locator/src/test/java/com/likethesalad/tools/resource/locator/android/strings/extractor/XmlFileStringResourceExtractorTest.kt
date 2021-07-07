package com.likethesalad.tools.resource.locator.android.strings.extractor

import com.google.common.truth.Truth
import com.likethesalad.tools.resource.locator.android.testutils.DummyResourcesFinder
import org.junit.Test
import java.io.File

class XmlFileStringResourceExtractorTest {

    @Test
    fun `Get all string resources from file`() {
        val file = getStringsXmlFile("strings_1.xml")

        val instance = createInstance(file)

        val result = instance.extract()
        Truth.assertThat(result.getFileSource()).isEqualTo(file)
        val resources = result.getResources()
        Truth.assertThat(resources.size).isEqualTo(2)
    }

    private fun createInstance(xmlFile: File): XmlFileStringResourceExtractor {
        return XmlFileStringResourceExtractor(xmlFile)
    }

    private fun getStringsXmlFile(name: String): File {
        return DummyResourcesFinder.getResourceFile("resources/strings/$name")
    }
}