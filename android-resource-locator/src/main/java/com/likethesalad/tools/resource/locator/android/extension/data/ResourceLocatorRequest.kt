package com.likethesalad.tools.resource.locator.android.extension.data

import com.likethesalad.tools.resource.locator.android.extension.listener.ResourceLocatorListener

data class ResourceLocatorRequest(
    val name: String,
    val configuration: ResourceLocatorConfiguration,
    val listener: ResourceLocatorListener?
)