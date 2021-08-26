package com.likethesalad.tools.resource.locator.android.extension.observer.data

import com.likethesalad.tools.resource.locator.android.ResourceLocatorTask
import org.gradle.api.tasks.TaskProvider
import java.io.File

data class ResourceLocatorTaskContainer(
    val taskProvider: TaskProvider<ResourceLocatorTask>,
    val outputDir: File,
    val taskContext: ResourceLocatorTaskContext
)