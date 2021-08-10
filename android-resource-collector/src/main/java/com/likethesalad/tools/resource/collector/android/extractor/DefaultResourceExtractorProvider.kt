package com.likethesalad.tools.resource.collector.android.extractor

import com.likethesalad.tools.resource.collector.android.source.AndroidXmlResourceSource
import com.likethesalad.tools.resource.collector.extractor.ResourceExtractor
import com.likethesalad.tools.resource.collector.extractor.ResourceExtractorProvider
import com.likethesalad.tools.resource.collector.source.ResourceSource

class DefaultResourceExtractorProvider(private val extractors: List<ResourceExtractor<AndroidXmlResourceSource>>) :
    ResourceExtractorProvider {

    @Suppress("UNCHECKED_CAST")
    override fun getExtractors(): List<ResourceExtractor<ResourceSource>> {
        return extractors as List<ResourceExtractor<ResourceSource>>
    }
}