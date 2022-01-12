package com.likethesalad.tools.resource.locator.android.extension

import com.likethesalad.tools.resource.locator.android.extension.data.ResourceLocatorConfiguration
import com.likethesalad.tools.resource.locator.android.extension.data.ResourceLocatorRequest
import com.likethesalad.tools.resource.locator.android.extension.listener.ResourceLocatorListener
import com.likethesalad.tools.resource.serializer.ResourceSerializer

open class AndroidResourceLocatorExtension(
    private val resourceSerializer: ResourceSerializer,
) {
    private val locatorRequests = mutableMapOf<String, ResourceLocatorRequest>()

    fun getResourceSerializer(): ResourceSerializer {
        return resourceSerializer
    }

    fun registerLocator(
        name: String,
        configuration: ResourceLocatorConfiguration,
        listener: ResourceLocatorListener? = null
    ) {
        if (name in locatorRequests) {
            throw IllegalArgumentException("Locator already registered with name $name")
        }
        locatorRequests[name] = ResourceLocatorRequest(name, configuration, listener)
    }

    internal fun getLocatorRequests(): List<ResourceLocatorRequest> {
        return locatorRequests.values.toList()
    }
}