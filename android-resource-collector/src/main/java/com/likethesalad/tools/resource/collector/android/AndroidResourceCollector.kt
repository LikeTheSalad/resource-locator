package com.likethesalad.tools.resource.collector.android

import com.google.auto.factory.AutoFactory
import com.likethesalad.tools.android.plugin.AndroidExtension
import com.likethesalad.tools.resource.api.android.AndroidResource
import com.likethesalad.tools.resource.collector.ResourceCollector
import com.likethesalad.tools.resource.collector.android.data.resdir.ResDirFinder
import com.likethesalad.tools.resource.collector.android.data.variant.VariantTree
import com.likethesalad.tools.resource.collector.android.di.CollectorModule
import com.likethesalad.tools.resource.collector.android.di.DaggerCollectorComponent
import com.likethesalad.tools.resource.collector.android.extractor.DefaultResourceExtractorProvider
import com.likethesalad.tools.resource.collector.android.extractor.XmlResourceExtractor
import com.likethesalad.tools.resource.collector.android.merger.VariantResourceMerger
import com.likethesalad.tools.resource.collector.android.source.providers.ComposableResourceSourceProvider
import com.likethesalad.tools.resource.collector.android.source.providers.VariantTreeResourceSourceProvider
import com.likethesalad.tools.resource.collector.extractor.ResourceExtractorProvider
import com.likethesalad.tools.resource.collector.merger.ResourceMerger
import com.likethesalad.tools.resource.collector.source.ResourceSourceProvider

@AutoFactory
class AndroidResourceCollector internal constructor(
    private val androidExtension: AndroidExtension,
    private val variantTree: VariantTree,
    private val resourceExtractor: XmlResourceExtractor<out AndroidResource>
) : ResourceCollector() {

    companion object {
        private val component by lazy {
            DaggerCollectorComponent.builder()
                .collectorModule(CollectorModule())
                .build()
        }

        fun newInstance(
            androidExtension: AndroidExtension,
            variantTree: VariantTree,
            resourceExtractor: XmlResourceExtractor<out AndroidResource>
        ): AndroidResourceCollector {
            val variantTreeResourceProvider = createVariantTreeResourceProvider(
                variantTree,
                ResDirFinder(androidExtension)
            )
            val collector = component.androidResourceCollectorFactory()
                .create(androidExtension, variantTree, resourceExtractor)

            val sourceProvider = collector.getSourceProvider() as ComposableResourceSourceProvider
            sourceProvider.addProvider(variantTreeResourceProvider)

            return collector
        }

        private fun createVariantTreeResourceProvider(
            variantTree: VariantTree,
            resDirFinder: ResDirFinder
        ): VariantTreeResourceSourceProvider {
            return component.variantTreeResourceSourceProviderFactory()
                .create(variantTree, resDirFinder)
        }
    }

    private val resourceSourceProvider by lazy {
        ComposableResourceSourceProvider()
    }

    override fun getSourceProvider(): ResourceSourceProvider {
        return resourceSourceProvider
    }

    override fun getExtractorProvider(): ResourceExtractorProvider {
        return DefaultResourceExtractorProvider(listOf(resourceExtractor))
    }

    override fun getMerger(): ResourceMerger {
        return VariantResourceMerger(variantTree)
    }
}