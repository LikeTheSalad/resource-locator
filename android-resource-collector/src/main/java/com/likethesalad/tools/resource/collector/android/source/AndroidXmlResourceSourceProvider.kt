package com.likethesalad.tools.resource.collector.android.source

import com.likethesalad.tools.resource.collector.android.data.resdir.ResDirFinder
import com.likethesalad.tools.resource.collector.android.data.valuedir.ValueDirFinder
import com.likethesalad.tools.resource.collector.android.data.variant.VariantTree
import com.likethesalad.tools.resource.collector.android.data.xml.XmlFileFinder
import com.likethesalad.tools.resource.collector.source.ResourceSource
import com.likethesalad.tools.resource.collector.source.ResourceSourceProvider

class AndroidXmlResourceSourceProvider(
    private val variantTree: VariantTree,
    private val resDirFinder: ResDirFinder,
    private val valueDirFinder: ValueDirFinder,
    private val xmlFileFinder: XmlFileFinder
) : ResourceSourceProvider {

    override fun getSources(): List<ResourceSource> {
        TODO("Not yet implemented")
    }

    /*override fun getSources(): List<ResourceSource> {
        val sources = mutableListOf<AndroidXmlResourceSource>()

        val variants = variantTree.getVariants()
        for (variant in variants) {
            val resDirs = resDirFinder.findResDirs(variant)
            for (resDir in resDirs) {
                val valueDirs = valueDirFinder.findValueDirs(resDir)
                for (valueDir in valueDirs) {
                    val scope = AndroidResourceScope(valueDir.resDir.variant, valueDir.language)
                    val xmlFiles = getXmlFiles(valueDir.dir)
                    for (file in xmlFiles) {
                        sources.add(AndroidXmlResourceSource(file, scope))
                    }
                }
            }
        }

        return sources
    }

    private fun getXmlFiles(dir: File): List<File> {

    }*/
}