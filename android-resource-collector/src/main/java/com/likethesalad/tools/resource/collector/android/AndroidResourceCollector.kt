package com.likethesalad.tools.resource.collector.android

import com.likethesalad.tools.resource.api.android.AndroidResource
import com.likethesalad.tools.resource.collector.ResourceCollector
import com.likethesalad.tools.resource.collector.android.data.variant.VariantTree
import com.likethesalad.tools.resource.collector.android.di.CollectorComponentProvider
import com.likethesalad.tools.resource.collector.android.extractor.DefaultResourceExtractorProvider
import com.likethesalad.tools.resource.collector.android.extractor.XmlResourceExtractor
import com.likethesalad.tools.resource.collector.android.merger.VariantResourceMerger
import com.likethesalad.tools.resource.collector.android.source.providers.ComposableResourceSourceProvider
import com.likethesalad.tools.resource.collector.extractor.ResourceExtractorProvider
import com.likethesalad.tools.resource.collector.merger.ResourceMerger
import com.likethesalad.tools.resource.collector.source.ResourceSourceProvider
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class AndroidResourceCollector @AssistedInject internal constructor(
    @Assisted private val variantTree: VariantTree,
    @Assisted private val resourceExtractor: XmlResourceExtractor<out AndroidResource>
) : ResourceCollector() {

    @AssistedFactory
    interface Factory {
        fun create(
            variantTree: VariantTree,
            resourceExtractor: XmlResourceExtractor<out AndroidResource>
        ): AndroidResourceCollector
    }

    companion object {
        fun newInstance(
            variantTree: VariantTree,
            resourceExtractor: XmlResourceExtractor<out AndroidResource>
        ): AndroidResourceCollector {
            return CollectorComponentProvider.getComponent()
                .androidResourceCollectorFactory()
                .create(variantTree, resourceExtractor)
        }
    }

    private val resourceSourceProvider by lazy {
        ComposableResourceSourceProvider()
    }

    fun getComposableSourceProvider(): ComposableResourceSourceProvider {
        return resourceSourceProvider
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