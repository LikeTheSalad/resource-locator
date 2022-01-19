package com.likethesalad.tools.resource.locator.android.extension

import com.likethesalad.tools.resource.locator.android.extension.configuration.ResourceLocatorEntryPoint
import com.likethesalad.tools.resource.locator.android.extension.configuration.source.utils.CommonSourceConfigurationCreator
import com.likethesalad.tools.resource.locator.android.extension.data.ResourceLocatorRequest
import com.likethesalad.tools.resource.locator.android.extension.listener.ResourceLocatorCreationListener

open class AndroidResourceLocatorExtension(
    private val commonSourceConfigurationCreator: CommonSourceConfigurationCreator
) {
    private val locatorRequests = mutableMapOf<String, ResourceLocatorRequest>()

    fun getCommonSourceConfigurationCreator(): CommonSourceConfigurationCreator {
        return commonSourceConfigurationCreator
    }

    fun registerLocator(
        name: String,
        entryPoint: ResourceLocatorEntryPoint,
        listener: ResourceLocatorCreationListener? = null
    ) {
        if (name in locatorRequests) {
            throw IllegalArgumentException("Locator already registered with name $name")
        }
        locatorRequests[name] = ResourceLocatorRequest(name, entryPoint, listener)
    }

    internal fun getLocatorRequests(): List<ResourceLocatorRequest> {
        return locatorRequests.values.toList()
    }
}