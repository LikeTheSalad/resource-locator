package com.likethesalad.tools.resource.locator.android.extension

import com.likethesalad.tools.resource.locator.android.extension.observer.ResourceLocatorTaskContainer
import org.gradle.api.Action
import java.io.File

open class ResourceLocatorExtension {

    fun onResourceLocatorTaskCreated(action: Action<ResourceLocatorTaskContainer>) {

    }

    fun getResourcesFromDir(directory: File): LanguageResourceFinder {
        TODO()
    }
}