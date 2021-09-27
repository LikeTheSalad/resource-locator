package com.likethesalad.android.string.resources.locator

import com.likethesalad.android.string.resources.locator.extractor.StringXmlResourceExtractor
import com.likethesalad.tools.resource.collector.ResourceCollector
import com.likethesalad.tools.resource.collector.android.AndroidResourceCollector
import com.likethesalad.tools.resource.collector.android.data.variant.VariantTree
import com.likethesalad.tools.resource.locator.android.AndroidResourceLocatorPlugin

class StringResourceLocatorPlugin : AndroidResourceLocatorPlugin() {

    private val extractor by lazy {
        StringXmlResourceExtractor()
    }

    override fun getLocatorId(): String = "string"

    override fun getResourceCollector(variantTree: VariantTree): ResourceCollector {
        return AndroidResourceCollector.newInstance(getAndroidExtension(), variantTree, extractor)
    }
}