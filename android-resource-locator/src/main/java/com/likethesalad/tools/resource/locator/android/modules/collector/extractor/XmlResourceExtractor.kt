package com.likethesalad.tools.resource.locator.android.modules.collector.extractor

import com.likethesalad.tools.resource.api.android.AndroidResource
import com.likethesalad.tools.resource.api.android.collection.FileResourceCollection
import com.likethesalad.tools.resource.api.collection.ResourceCollection
import com.likethesalad.tools.resource.collector.extractor.ResourceExtractor
import com.likethesalad.tools.resource.locator.android.modules.collector.source.AndroidXmlResourceSource
import com.likethesalad.tools.resource.locator.android.tools.xml.AndroidXmlResDocument

abstract class XmlResourceExtractor<T : AndroidResource<out Any>> : ResourceExtractor<AndroidXmlResourceSource>() {

    override fun doExtract(source: AndroidXmlResourceSource): ResourceCollection {
        val resources = getResourcesFromAndroidDocument(source.document)

        return FileResourceCollection(resources, source.getFileSource(), source.getScope())
    }

    abstract fun getResourcesFromAndroidDocument(document: AndroidXmlResDocument): List<T>
}