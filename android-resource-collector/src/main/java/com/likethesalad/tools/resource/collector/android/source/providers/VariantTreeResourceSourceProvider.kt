package com.likethesalad.tools.resource.collector.android.source.providers

import com.google.auto.factory.AutoFactory
import com.google.auto.factory.Provided
import com.likethesalad.tools.resource.api.android.environment.Variant
import com.likethesalad.tools.resource.collector.android.data.resdir.ResDir
import com.likethesalad.tools.resource.collector.android.data.resdir.ResDirFinder
import com.likethesalad.tools.resource.collector.android.data.variant.VariantTree
import com.likethesalad.tools.resource.collector.source.ResourceSource
import com.likethesalad.tools.resource.collector.source.ResourceSourceProvider

@AutoFactory
class VariantTreeResourceSourceProvider(
    private val variantTree: VariantTree,
    private val resDirFinder: ResDirFinder,
    @Provided private val resDirResourceSourceProviderFactory: ResDirResourceSourceProviderFactory
) : ResourceSourceProvider {

    private val lazySources: List<ResourceSource> by lazy {
        val sources = mutableListOf<ResourceSource>()
        sources.addAll(getResourceSourcesFromVariants())
        sources
    }

    override fun getSources(): List<ResourceSource> {
        return lazySources
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
        return resDirResourceSourceProviderFactory.create(resDir).getSources()
    }
}