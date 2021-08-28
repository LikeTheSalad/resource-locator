package com.likethesalad.tools.resource.locator.android.test

import com.likethesalad.tools.android.plugin.AndroidExtension
import com.likethesalad.tools.resource.collector.ResourceCollector
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
        androidExtension: AndroidExtension,
        variantTree: VariantTree
    ): ResourceCollector {
        return AndroidResourceCollector.newInstance(androidExtension, variantTree, resourceExtractor)
    }

}