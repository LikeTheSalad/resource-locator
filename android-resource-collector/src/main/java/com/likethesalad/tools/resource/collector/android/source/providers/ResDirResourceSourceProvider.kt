package com.likethesalad.tools.resource.collector.android.source.providers

import com.likethesalad.tools.resource.api.android.impl.AndroidResourceScope
import com.likethesalad.tools.resource.collector.android.data.resdir.ResDir
import com.likethesalad.tools.resource.collector.android.data.valuedir.ValueDir
import com.likethesalad.tools.resource.collector.android.data.valuedir.ValueDirFinder
import com.likethesalad.tools.resource.collector.android.data.xml.XmlFileFinder
import com.likethesalad.tools.resource.collector.android.source.AndroidXmlResourceSource
import com.likethesalad.tools.resource.collector.source.ResourceSource
import com.likethesalad.tools.resource.collector.source.ResourceSourceProvider
import java.io.File

class ResDirResourceSourceProvider(private val resDir: ResDir) : ResourceSourceProvider() {

    override fun doGetSources(): List<ResourceSource> {
        val sources = mutableListOf<ResourceSource>()
        val valueDirs = ValueDirFinder.findValueDirs(resDir)

        for (valueDir in valueDirs) {
            sources.addAll(extractSourcesFromValueDir(valueDir))
        }

        return sources
    }

    private fun extractSourcesFromValueDir(valueDir: ValueDir): Collection<ResourceSource> {
        val sources = mutableListOf<ResourceSource>()
        val xmlFiles = XmlFileFinder.findXmlFiles(valueDir)
        val scope = AndroidResourceScope(valueDir.resDir.variant, valueDir.language)

        for (file in xmlFiles) {
            sources.add(createAndroidResourceSource(file, scope))
        }

        return sources
    }

    private fun createAndroidResourceSource(
        file: File,
        scope: AndroidResourceScope
    ): AndroidXmlResourceSource = AndroidXmlResourceSource(file, scope)
}