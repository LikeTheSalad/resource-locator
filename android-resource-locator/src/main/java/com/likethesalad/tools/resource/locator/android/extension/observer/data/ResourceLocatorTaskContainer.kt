package com.likethesalad.tools.resource.locator.android.extension.observer.data

import org.gradle.api.file.DirectoryProperty

data class ResourceLocatorTaskContainer(
    val outputDir: DirectoryProperty,
    val taskContext: ResourceLocatorTaskContext
)