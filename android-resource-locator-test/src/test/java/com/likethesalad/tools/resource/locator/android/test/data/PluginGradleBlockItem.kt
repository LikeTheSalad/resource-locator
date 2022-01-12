package com.likethesalad.tools.resource.locator.android.test.data

import com.likethesalad.tools.functional.testing.app.layout.GradleBlockItem

class PluginGradleBlockItem(private val locatorName: String) : GradleBlockItem {

    override fun getItemText(): String {
        return """
            testResourceLocator.registerLocator("$locatorName", new com.likethesalad.tools.resource.locator.android.extension.configuration.DefaultResourceLocatorConfiguration(), null)
        """.trimIndent()
    }
}