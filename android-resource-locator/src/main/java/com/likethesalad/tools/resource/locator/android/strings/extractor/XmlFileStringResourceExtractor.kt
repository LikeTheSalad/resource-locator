package com.likethesalad.tools.resource.locator.android.strings.extractor

import com.likethesalad.tools.resource.extractor.ResourceExtractor
import com.likethesalad.tools.resource.locator.android.collection.XmlFileResourceCollection
import com.likethesalad.tools.resource.locator.android.strings.AndroidStringResource

class XmlFileStringResourceExtractor(private val xmlFile: String) : ResourceExtractor<AndroidStringResource> {

    override fun extract(): XmlFileResourceCollection<AndroidStringResource> {
        TODO("Not yet implemented")
    }
}