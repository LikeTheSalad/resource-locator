package com.likethesalad.tools.resource.collector.android.source

import com.likethesalad.tools.resource.api.android.AndroidResourceScope
import com.likethesalad.tools.resource.api.android.environment.Variant
import com.likethesalad.tools.resource.collector.android.data.AndroidXmlResDocument
import com.likethesalad.tools.resource.collector.android.data.resdir.ResDir
import com.likethesalad.tools.resource.collector.android.data.resdir.ResDirFinder
import com.likethesalad.tools.resource.collector.android.data.valuedir.ValueDir
import com.likethesalad.tools.resource.collector.android.data.valuedir.ValueDirFinder
import com.likethesalad.tools.resource.collector.android.data.variant.VariantTree
import com.likethesalad.tools.resource.collector.android.data.xml.XmlFileFinder
import com.likethesalad.tools.resource.collector.source.ResourceSource
import com.likethesalad.tools.resource.collector.source.ResourceSourceProvider
import javax.xml.parsers.DocumentBuilderFactory

class AndroidXmlResourceSourceProvider(
    private val variantTree: VariantTree,
    private val resDirFinder: ResDirFinder,
    private val valueDirFinder: ValueDirFinder,
    private val xmlFileFinder: XmlFileFinder
) : ResourceSourceProvider {

    override fun getSources(): List<ResourceSource> {
        val sources = mutableListOf<ResourceSource>()
        val variants = variantTree.getVariants()

        for (variant in variants) {
            sources.addAll(extractSourcesFromVariant(variant))
        }

        return sources
    }

    private fun extractSourcesFromVariant(variant: Variant): List<ResourceSource> {
        val sources = mutableListOf<ResourceSource>()
        val resDirs = resDirFinder.findResDirs(variant)

        for (resDir in resDirs) {
            sources.addAll(extractSourcesFromResDir(resDir))
        }

        return sources
    }

    private fun extractSourcesFromResDir(resDir: ResDir): List<ResourceSource> {
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
            sources.add(
                AndroidXmlResourceSource(
                    file,
                    scope,
                    AndroidXmlResDocument.Factory(DocumentBuilderFactory.newInstance())
                )
            )
        }

        return sources
    }

}