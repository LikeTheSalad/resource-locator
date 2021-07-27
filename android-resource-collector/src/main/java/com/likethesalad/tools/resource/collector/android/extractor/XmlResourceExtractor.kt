package com.likethesalad.tools.resource.collector.android.extractor

import com.likethesalad.tools.resource.api.android.AndroidResource
import com.likethesalad.tools.resource.api.collection.ResourceCollection
import com.likethesalad.tools.resource.collector.android.collection.FileResourceCollection
import com.likethesalad.tools.resource.collector.android.data.AndroidXmlResDocument
import com.likethesalad.tools.resource.collector.android.source.AndroidXmlResourceSource
import com.likethesalad.tools.resource.collector.extractor.ResourceExtractor

abstract class XmlResourceExtractor<T : AndroidResource<out Any>> : ResourceExtractor<AndroidXmlResourceSource>() {

    override fun doExtract(source: AndroidXmlResourceSource): ResourceCollection {
        val resources = getResourcesFromAndroidDocument(source.document)

        return FileResourceCollection(resources, source.getFileSource(), source.getScope())
    }

    abstract fun getResourcesFromAndroidDocument(document: AndroidXmlResDocument): List<T>
}