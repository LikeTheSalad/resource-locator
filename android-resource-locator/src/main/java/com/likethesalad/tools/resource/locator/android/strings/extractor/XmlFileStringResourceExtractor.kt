package com.likethesalad.tools.resource.locator.android.strings.extractor

import com.likethesalad.tools.resource.extractor.ResourceExtractor
import com.likethesalad.tools.resource.locator.android.collection.FileResourceCollection
import com.likethesalad.tools.resource.locator.android.strings.AndroidStringResource

class XmlFileStringResourceExtractor(private val xmlFile: String) : ResourceExtractor<AndroidStringResource> {

    override fun extract(): FileResourceCollection<AndroidStringResource> {
        TODO("Not yet implemented")
    }
}