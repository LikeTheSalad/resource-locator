package com.likethesalad.tools.resource.locator.android.extension.listener

import com.likethesalad.tools.resource.locator.android.extension.configuration.data.ResourceLocatorInfo

interface ResourceLocatorCreationListener {
    fun onLocatorReady(type: String, info: ResourceLocatorInfo)
}