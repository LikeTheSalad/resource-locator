package com.likethesalad.tools.resource.collector.android

import com.google.auto.factory.AutoFactory
import com.google.auto.factory.Provided
import com.likethesalad.tools.resource.api.android.AndroidResource
import com.likethesalad.tools.resource.collector.ResourceCollector
import com.likethesalad.tools.resource.collector.android.data.variant.VariantTree
import com.likethesalad.tools.resource.collector.android.extractor.DefaultResourceExtractorProvider
import com.likethesalad.tools.resource.collector.android.extractor.XmlResourceExtractor
import com.likethesalad.tools.resource.collector.android.merger.VariantResourceMerger
import com.likethesalad.tools.resource.collector.android.source.AndroidXmlResourceSourceProviderFactory
import com.likethesalad.tools.resource.collector.extractor.ResourceExtractorProvider
import com.likethesalad.tools.resource.collector.merger.ResourceMerger
import com.likethesalad.tools.resource.collector.source.ResourceSourceProvider

@AutoFactory
class AndroidResourceCollector(
    private val variantTree: VariantTree,
    private val resourceExtractor: XmlResourceExtractor<out AndroidResource>,
    @Provided androidXmlResourceSourceProviderFactory: AndroidXmlResourceSourceProviderFactory
) : ResourceCollector() {

    private val resourceSourceProvider by lazy {
        androidXmlResourceSourceProviderFactory.create(variantTree)
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