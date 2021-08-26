package com.likethesalad.tools.resource.locator.android.extension

import com.likethesalad.tools.resource.locator.android.extension.observer.ResourceLocatorTaskContainer
import com.likethesalad.tools.resource.locator.android.extension.observer.ResourceLocatorTaskPublisher
import com.likethesalad.tools.resource.locator.android.extension.observer.observers.ActionResourceLocatorTaskObserver
import org.gradle.api.Action
import java.io.File

open class ResourceLocatorExtension(
    private val taskPublisher: ResourceLocatorTaskPublisher,
    private val languageResourceFinderFactory: LanguageResourceFinderFactory
) {

    fun onResourceLocatorTaskCreated(action: Action<ResourceLocatorTaskContainer>) {
        taskPublisher.register(ActionResourceLocatorTaskObserver(action))
    }

    fun getResourcesFromDir(directory: File): LanguageResourceFinder {
        return languageResourceFinderFactory.create(directory)
    }
}