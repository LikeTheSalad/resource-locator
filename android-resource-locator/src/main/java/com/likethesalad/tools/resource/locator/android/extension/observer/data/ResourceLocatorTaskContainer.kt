package com.likethesalad.tools.resource.locator.android.extension.observer.data

import org.gradle.api.file.FileCollection

data class ResourceLocatorTaskContainer(
    val outputDir: FileCollection,
    val taskContext: ResourceLocatorTaskContext
)