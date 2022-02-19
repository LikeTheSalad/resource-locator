package com.likethesalad.tools.resource.locator.android.extension.data

import com.likethesalad.tools.resource.locator.android.extension.configuration.ResourceLocatorEntryPoint
import com.likethesalad.tools.resource.locator.android.extension.listener.ResourceLocatorCreationListener

data class ResourceLocatorRequest(
    val name: String,
    val entryPoint: ResourceLocatorEntryPoint,
    val listener: ResourceLocatorCreationListener?
)