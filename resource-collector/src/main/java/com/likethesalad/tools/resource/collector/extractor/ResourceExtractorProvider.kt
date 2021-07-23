package com.likethesalad.tools.resource.collector.extractor

interface ResourceExtractorProvider {
    fun getExtractors(): List<ResourceExtractor>
}