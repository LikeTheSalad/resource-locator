package com.likethesalad.tools.resource.collector.android.data.xml

import com.likethesalad.tools.resource.collector.android.data.valuedir.ValueDir
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class XmlFileFinder @Inject constructor() {

    companion object {
        private val XML_FORMAT = Regex(".+\\.[xX][mM][lL]\$")
    }

    fun findXmlFiles(valueDir: ValueDir): List<File> {
        return valueDir.dir.listFiles { _, name ->
            isXmlFile(name)
        }?.toList() ?: emptyList()
    }

    private fun isXmlFile(name: String): Boolean {
        return XML_FORMAT.matches(name)
    }
}