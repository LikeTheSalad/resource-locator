package com.likethesalad.tools.resource.collector.android

import com.google.auto.factory.AutoFactory
import com.google.auto.factory.Provided
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
import com.likethesalad.tools.resource.collector.android.source.AndroidXmlResourceSourceProviderFactory
import com.likethesalad.tools.resource.collector.android.source.extra.AndroidXmlExtraSourceProvider
import com.likethesalad.tools.resource.collector.extractor.ResourceExtractorProvider
import com.likethesalad.tools.resource.collector.merger.ResourceMerger
import com.likethesalad.tools.resource.collector.source.ResourceSourceProvider

@AutoFactory
class AndroidResourceCollector internal constructor(
    private val androidExtension: AndroidExtension,
    private val variantTree: VariantTree,
    private val resourceExtractor: XmlResourceExtractor<out AndroidResource>,
    private val extraXmlSourceProviders: List<AndroidXmlExtraSourceProvider>,
    @Provided androidXmlResourceSourceProviderFactory: AndroidXmlResourceSourceProviderFactory
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
            resourceExtractor: XmlResourceExtractor<out AndroidResource>,
            extraXmlSourceProviders: List<AndroidXmlExtraSourceProvider>
        ): AndroidResourceCollector {
            return component.androidResourceCollectorFactory()
                .create(androidExtension, variantTree, resourceExtractor, extraXmlSourceProviders)
        }
    }

    private val resourceSourceProvider by lazy {
        androidXmlResourceSourceProviderFactory.create(
            variantTree,
            ResDirFinder(androidExtension),
            extraXmlSourceProviders
        )
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