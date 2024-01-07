package com.likethesalad.tools.resource.collector.android.source.providers

import com.likethesalad.tools.agpcompat.api.bridges.AndroidExtension
import com.likethesalad.tools.resource.api.android.environment.Variant
import com.likethesalad.tools.resource.collector.android.data.resdir.ResDir
import com.likethesalad.tools.resource.collector.android.data.resdir.ResDirFinder
import com.likethesalad.tools.resource.collector.android.data.variant.VariantTree
import com.likethesalad.tools.resource.collector.source.ResourceSource
import com.likethesalad.tools.resource.collector.source.ResourceSourceProvider

class VariantTreeResourceSourceProvider(
    private val androidExtension: AndroidExtension,
    private val variantTree: VariantTree
) : ResourceSourceProvider() {

    private val lazySources: List<ResourceSource> by lazy {
        val sources = mutableListOf<ResourceSource>()
        sources.addAll(getResourceSourcesFromVariants())
        sources
    }

    override fun doGetSources(): List<ResourceSource> {
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
        val resDirs = ResDirFinder.findResDirs(androidExtension, variant)

        for (resDir in resDirs) {
            sources.addAll(extractSourcesFromResDir(resDir))
        }

        return sources
    }

    private fun extractSourcesFromResDir(resDir: ResDir): List<ResourceSource> {
        return ResDirResourceSourceProvider(resDir).getSources()
    }
}