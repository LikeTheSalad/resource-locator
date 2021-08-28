package com.likethesalad.tools.resource.collector.android.extractor

import com.likethesalad.tools.resource.api.ResourceScope
import com.likethesalad.tools.resource.api.android.AndroidResource
import com.likethesalad.tools.resource.api.collection.BasicResourceCollection
import com.likethesalad.tools.resource.api.collection.ResourceCollection
import com.likethesalad.tools.resource.collector.android.data.AndroidXmlResDocument
import com.likethesalad.tools.resource.collector.android.source.AndroidXmlResourceSource
import com.likethesalad.tools.resource.collector.extractor.ResourceExtractor

abstract class XmlResourceExtractor<T : AndroidResource> : ResourceExtractor<AndroidXmlResourceSource>() {

    override fun doExtract(source: AndroidXmlResourceSource): ResourceCollection {
        val resources = getResourcesFromAndroidDocument(source.document, source.getScope())

        return BasicResourceCollection(resources)
    }

    abstract fun getResourcesFromAndroidDocument(
        document: AndroidXmlResDocument,
        scope: ResourceScope
    ): List<T>
}