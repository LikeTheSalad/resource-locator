package com.likethesalad.tools.resource.locator.android.extension

import com.likethesalad.tools.resource.locator.android.extension.configuration.ResourceLocatorConfiguration
import com.likethesalad.tools.resource.locator.android.extension.observer.ResourceLocatorTaskPublisher
import com.likethesalad.tools.resource.locator.android.extension.observer.data.ResourceLocatorTaskContainer
import com.likethesalad.tools.resource.locator.android.extension.observer.observers.ActionResourceLocatorTaskObserver
import com.likethesalad.tools.resource.locator.android.extension.resources.DirLanguageCollectorProvider
import com.likethesalad.tools.resource.locator.android.extension.resources.LanguageResourcesHandler
import com.likethesalad.tools.resource.serializer.ResourceSerializer
import org.gradle.api.Action
import java.io.File

open class ResourceLocatorExtension(
    private val taskPublisher: ResourceLocatorTaskPublisher,
    private val dirLanguageCollectorProviderFactory: DirLanguageCollectorProvider.Factory,
    private val resourceSerializer: ResourceSerializer,
) {
    private val resourceLocatorConfigurations = mutableMapOf<String, ResourceLocatorConfiguration>()

    fun onResourceLocatorTaskCreated(action: Action<ResourceLocatorTaskContainer>) {
        taskPublisher.register(ActionResourceLocatorTaskObserver(action))
    }

    fun getResourcesFromDir(directory: File): LanguageResourcesHandler {
        return LanguageResourcesHandler(dirLanguageCollectorProviderFactory.create(directory))
    }

    fun getResourceSerializer(): ResourceSerializer {
        return resourceSerializer
    }

    fun getConfiguration(variantName: String): ResourceLocatorConfiguration {
        if (!resourceLocatorConfigurations.containsKey(variantName)) {
            resourceLocatorConfigurations[variantName] = ResourceLocatorConfiguration()
        }

        return resourceLocatorConfigurations.getValue(variantName)
    }
}