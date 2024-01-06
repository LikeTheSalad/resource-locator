package com.likethesalad.tools.resource.locator.android.test.data

import com.likethesalad.tools.functional.testing.blocks.GradleBlockItem

class PluginGradleBlockItem(private val locatorName: String) : GradleBlockItem {

    override fun getItemText(): String {
        return """
            testResourceLocator.registerLocator("$locatorName", new com.likethesalad.tools.resource.locator.android.test.locator.TestLocatorEntryPoint(testResourceLocator.getCommonSourceConfigurationCreator()), null)
        """.trimIndent()
    }
}