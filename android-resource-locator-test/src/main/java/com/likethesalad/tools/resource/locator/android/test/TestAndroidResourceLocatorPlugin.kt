package com.likethesalad.tools.resource.locator.android.test

import com.likethesalad.android.string.resources.locator.StringResourceLocatorPlugin

class TestAndroidResourceLocatorPlugin : StringResourceLocatorPlugin() {

    override fun getLocatorId(): String = "test"
}