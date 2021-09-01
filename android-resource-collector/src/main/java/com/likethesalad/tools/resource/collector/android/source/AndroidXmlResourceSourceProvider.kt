package com.likethesalad.tools.resource.collector.android.source

import com.google.auto.factory.AutoFactory
import com.google.auto.factory.Provided
import com.likethesalad.tools.resource.api.android.AndroidResourceScope
import com.likethesalad.tools.resource.api.android.environment.Variant
import com.likethesalad.tools.resource.collector.android.data.resdir.ResDir
import com.likethesalad.tools.resource.collector.android.data.resdir.ResDirFinder
import com.likethesalad.tools.resource.collector.android.data.valuedir.ValueDir
import com.likethesalad.tools.resource.collector.android.data.valuedir.ValueDirFinder
import com.likethesalad.tools.resource.collector.android.data.variant.VariantTree
import com.likethesalad.tools.resource.collector.android.data.xml.XmlFileFinder
import com.likethesalad.tools.resource.collector.android.source.extra.AndroidXmlExtraSourceProvider
import com.likethesalad.tools.resource.collector.source.ResourceSource
import com.likethesalad.tools.resource.collector.source.ResourceSourceProvider
import java.io.File

@AutoFactory
class AndroidXmlResourceSourceProvider(
    private val variantTree: VariantTree,
    private val resDirFinder: ResDirFinder,
    private val extraXmlProviders: List<AndroidXmlExtraSourceProvider>,
    @Provided private val valueDirFinder: ValueDirFinder,
    @Provided private val xmlFileFinder: XmlFileFinder,
    @Provided private val sourceFactory: AndroidXmlResourceSourceFactory
) : ResourceSourceProvider {

    override fun getSources(): List<ResourceSource> {
        val sources = mutableListOf<ResourceSource>()

        sources.addAll(getResourceSourcesFromVariants())
        sources.addAll(getResourcesFromExtraXmlProviders())

        return sources
    }

    private fun getResourcesFromExtraXmlProviders(): List<ResourceSource> {
        val sources = mutableListOf<ResourceSource>()
        val sourceDescriptors = extraXmlProviders.flatMap { it.getXmlDescriptors() }

        sourceDescriptors.forEach { descriptor ->
            sources.add(createAndroidResourceSource(descriptor.file, descriptor.scope))
        }

        return sources
    }

    private fun getResourceSourcesFromVariants(): List<ResourceSource> {
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
            sources.add(createAndroidResourceSource(file, scope))
        }

        return sources
    }

    private fun createAndroidResourceSource(
        file: File,
        scope: AndroidResourceScope
    ): AndroidXmlResourceSource = sourceFactory.create(file, scope)

}