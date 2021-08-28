package com.likethesalad.tools.resource.locator.android

import com.likethesalad.tools.functional.testing.AndroidProjectTest
import com.likethesalad.tools.functional.testing.data.JarParameters
import org.junit.Test

class ResourceLocatorTest : AndroidProjectTest() {

    @Test
    fun `Check gathering strings from single variant`() {

    }

    override fun getAndroidBuildPluginVersion(): String = "3.3.3"

    override fun getGradleVersion(): String = "4.20.3"

    override fun getPluginJarParameters(): JarParameters {
        return JarParameters("android-resource-locator", "1.0.0")
    }

}