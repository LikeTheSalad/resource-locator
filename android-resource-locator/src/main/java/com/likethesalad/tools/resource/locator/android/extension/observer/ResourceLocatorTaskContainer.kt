package com.likethesalad.tools.resource.locator.android.extension.observer

import com.likethesalad.tools.resource.locator.android.ResourceLocatorTask
import org.gradle.api.tasks.TaskProvider

data class ResourceLocatorTaskContainer(
    val taskProvider: TaskProvider<ResourceLocatorTask>,
    val taskContext: ResourceLocatorTaskContext
)