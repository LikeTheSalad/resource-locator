package com.likethesalad.tools.resource.collector.android.source.providers

import com.google.auto.factory.AutoFactory
import com.google.auto.factory.Provided
import com.likethesalad.tools.resource.api.android.AndroidResourceScope
import com.likethesalad.tools.resource.collector.android.data.resdir.ResDir
import com.likethesalad.tools.resource.collector.android.data.valuedir.ValueDir
import com.likethesalad.tools.resource.collector.android.data.valuedir.ValueDirFinder
import com.likethesalad.tools.resource.collector.android.data.xml.XmlFileFinder
import com.likethesalad.tools.resource.collector.android.di.CollectorComponentProvider
import com.likethesalad.tools.resource.collector.android.source.AndroidXmlResourceSource
import com.likethesalad.tools.resource.collector.android.source.AndroidXmlResourceSourceFactory
import com.likethesalad.tools.resource.collector.source.ResourceSource
import com.likethesalad.tools.resource.collector.source.ResourceSourceProvider
import java.io.File

@AutoFactory
class ResDirResourceSourceProvider(
    private val resDir: ResDir,
    @Provided private val valueDirFinder: ValueDirFinder,
    @Provided private val xmlFileFinder: XmlFileFinder,
    @Provided private val sourceFactory: AndroidXmlResourceSourceFactory
) : ResourceSourceProvider {

    companion object {
        fun createInstance(resDir: ResDir): ResDirResourceSourceProvider {
            val component = CollectorComponentProvider.getComponent()
            return component.resDirResourceSourceProviderFactory()
                .create(resDir)
        }
    }

    override fun getSources(): List<ResourceSource> {
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