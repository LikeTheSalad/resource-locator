package com.likethesalad.tools.resourcelocator.android.strings.extractor

import com.likethesalad.tools.resourcelocator.android.collection.XmlFileResourceCollection
import com.likethesalad.tools.resourcelocator.android.strings.AndroidStringResource
import com.likethesalad.tools.resourcelocator.api.extraction.ResourceExtractor

class XmlFileStringResourceExtractor(private val xmlFile: String) : ResourceExtractor<AndroidStringResource> {

    override fun extract(): XmlFileResourceCollection<AndroidStringResource> {
        TODO("Not yet implemented")
    }
}