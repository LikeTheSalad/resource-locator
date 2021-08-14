package com.likethesalad.tools.resource.locator.android

import com.likethesalad.tools.resource.api.android.environment.Variant
import java.io.File

open class ResourceLocatorExtension {
    var resourceLocators: Set<ResourceLocator> = emptySet()


    fun resourceProviderFromDir(directory: File): ResourcesProvider {

    }

    fun tasksByVariant(variant: Variant): ResourceLocatorTaskManager {

    }
}