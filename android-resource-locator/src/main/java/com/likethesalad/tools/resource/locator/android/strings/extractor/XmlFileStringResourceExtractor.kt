package com.likethesalad.tools.resource.locator.android.strings.extractor

import com.likethesalad.tools.resource.extractor.ResourceExtractor
import com.likethesalad.tools.resource.locator.android.strings.AndroidStringResource
import com.likethesalad.tools.resource.locator.android.strings.collection.FileStringResourceCollection
import java.io.File

class XmlFileStringResourceExtractor(private val xmlFile: File) : ResourceExtractor<AndroidStringResource> {

    override fun extract(): FileStringResourceCollection {
        TODO("Not yet implemented")
    }
}