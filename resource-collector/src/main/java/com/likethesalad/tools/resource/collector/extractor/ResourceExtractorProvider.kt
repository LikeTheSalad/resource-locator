package com.likethesalad.tools.resource.collector.extractor

import com.likethesalad.tools.resource.collector.source.ResourceSource

interface ResourceExtractorProvider {
    fun getExtractors(): List<ResourceExtractor<ResourceSource>>
}