package com.likethesalad.tools.resource.locator.android.extension

import com.likethesalad.tools.resource.locator.android.extension.configuration.ResourceLocatorEntryPoint
import com.likethesalad.tools.resource.locator.android.extension.configuration.utils.CommonSourceConfigurationCreator
import com.likethesalad.tools.resource.locator.android.extension.data.ResourceLocatorRequest

open class AndroidResourceLocatorExtension(
    private val commonSourceConfigurationCreator: CommonSourceConfigurationCreator
) {
    private val locatorRequests = mutableMapOf<String, ResourceLocatorRequest>()

    fun getCommonSourceConfigurationCreator(): CommonSourceConfigurationCreator {
        return commonSourceConfigurationCreator
    }

    fun registerLocator(
        name: String,
        entryPoint: ResourceLocatorEntryPoint
    ) {
        if (name in locatorRequests) {
            throw IllegalArgumentException("Locator already registered with name $name")
        }
        locatorRequests[name] = ResourceLocatorRequest(name, entryPoint)
    }

    internal fun getLocatorRequests(): List<ResourceLocatorRequest> {
        return locatorRequests.values.toList()
    }
}