package com.likethesalad.tools.resource.locator.android.extension.data

import com.likethesalad.tools.resource.locator.android.extension.configuration.ResourceLocatorEntryPoint

data class ResourceLocatorRequest(
    val name: String,
    val entryPoint: ResourceLocatorEntryPoint
)