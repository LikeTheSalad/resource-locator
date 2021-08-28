package com.likethesalad.tools.resource.locator.android

import com.likethesalad.tools.functional.testing.AndroidProjectTest
import com.likethesalad.tools.functional.testing.data.JarParameters

class ResourceLocatorTest : AndroidProjectTest() {

    override fun getAndroidBuildPluginVersion(): String = "3.3.3"

    override fun getGradleVersion(): String = "4.20.3"

    override fun getPluginJarParameters(): JarParameters {
        return JarParameters("android-resource-locator", "1.0.0")
    }

}