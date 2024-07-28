package com.likethesalad.tools.resource.collector.android.data.xml

import com.google.common.truth.Truth
import com.likethesalad.tools.resource.api.android.environment.Language
import com.likethesalad.tools.resource.collector.android.data.resdir.ResDir
import com.likethesalad.tools.resource.collector.android.data.valuedir.ValueDir
import com.likethesalad.tools.testing.DummyResourcesFinder.getResourceFile
import io.mockk.mockk
import org.junit.Test
import java.io.ByteArrayInputStream
import java.io.File
import javax.xml.parsers.DocumentBuilderFactory


class XmlUtilsTest {

    @Test
    fun `Find xml files`() {
        val files = findXmlFilesFromFolder(getResourceFile("res/values"))

        Truth.assertThat(files).containsExactly(
            getResourceFile("res/values/anotherXml.xml"),
            getResourceFile("res/values/someFile.xml")
        )
    }

    @Test
    fun `Provide empty list when folder is empty`() {
        val files = findXmlFilesFromFolder(getResourceFile("res/values-es"))

        Truth.assertThat(files).isEmpty()
    }

    @Test
    fun `Provide empty list when no xml files are found`() {
        val files = findXmlFilesFromFolder(getResourceFile("res/values-es-rUS"))

        Truth.assertThat(files).isEmpty()
    }

    @Test
    fun `Extract text from node`() {
        val node = DocumentBuilderFactory
            .newInstance()
            .newDocumentBuilder()
            .parse(ByteArrayInputStream("<string name=\"the_name\">value <b>something bold</b></string>".toByteArray()))
            .documentElement

        Truth.assertThat(XmlUtils.getContents(node)).isEqualTo("value <b>something bold</b>")
    }

    private fun findXmlFilesFromFolder(folder: File): List<File> {
        val resDir = mockk<ResDir>()
        val valuesDir = ValueDir(resDir, folder, Language.Default)

        return XmlUtils.findXmlFiles(valuesDir)
    }
}