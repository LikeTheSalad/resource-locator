package com.likethesalad.tools.resource.collector.android.source.providers

import com.likethesalad.tools.resource.api.android.AndroidResourceScope
import com.likethesalad.tools.resource.collector.android.data.resdir.ResDir
import com.likethesalad.tools.resource.collector.android.data.valuedir.ValueDir
import com.likethesalad.tools.resource.collector.android.data.valuedir.ValueDirFinder
import com.likethesalad.tools.resource.collector.android.data.xml.XmlFileFinder
import com.likethesalad.tools.resource.collector.android.source.AndroidXmlResourceSource
import com.likethesalad.tools.resource.collector.source.ResourceSource
import com.likethesalad.tools.resource.collector.source.ResourceSourceProvider
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import java.io.File

class ResDirResourceSourceProvider @AssistedInject constructor(
    @Assisted private val resDir: ResDir,
    private val valueDirFinder: ValueDirFinder,
    private val xmlFileFinder: XmlFileFinder,
    private val sourceFactory: AndroidXmlResourceSource.Factory
) : ResourceSourceProvider() {

    @AssistedFactory
    interface Factory {
        fun create(resDir: ResDir): ResDirResourceSourceProvider
    }

    override fun doGetSources(): List<ResourceSource> {
        val sources = mutableListOf<ResourceSource>()
        val valueDirs = valueDirFinder.findValueDirs(resDir)

        for (valueDir in valueDirs) {
            sources.addAll(extractSourcesFromValueDir(valueDir))
        }

        return sources
    }

    private fun extractSourcesFromValueDir(valueDir: ValueDir): Collection<ResourceSource> {
        val sources = mutableListOf<ResourceSource>()
        val xmlFiles = xmlFileFinder.findXmlFiles(valueDir)
        val scope = AndroidResourceScope(valueDir.resDir.variant, valueDir.language)

        for (file in xmlFiles) {
            sources.add(createAndroidResourceSource(file, scope))
        }

        return sources
    }

    private fun createAndroidResourceSource(
        file: File,
        scope: AndroidResourceScope
    ): AndroidXmlResourceSource = sourceFactory.create(file, scope)
}