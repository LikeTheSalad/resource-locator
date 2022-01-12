package com.likethesalad.tools.resource.locator.android.test

import com.likethesalad.tools.resource.collector.android.AndroidResourceCollector
import com.likethesalad.tools.resource.collector.android.data.variant.VariantTree
import com.likethesalad.tools.resource.locator.android.AndroidResourceLocatorPlugin
import com.likethesalad.tools.resource.locator.android.test.extractor.TestXmlResourceExtractor

class TestAndroidResourceLocatorPlugin : AndroidResourceLocatorPlugin() {

    private val resourceExtractor by lazy {
        TestXmlResourceExtractor()
    }

    override fun getLocatorId(): String = "test"

    override fun getResourceCollector(
        variantTree: VariantTree
    ): AndroidResourceCollector {
        return AndroidResourceCollector.newInstance(variantTree, resourceExtractor)
    }

}