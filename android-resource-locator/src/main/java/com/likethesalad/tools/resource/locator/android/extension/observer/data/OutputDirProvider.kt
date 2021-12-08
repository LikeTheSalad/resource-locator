package com.likethesalad.tools.resource.locator.android.extension.observer.data

import com.likethesalad.tools.resource.locator.android.ResourceLocatorTask
import org.gradle.api.file.Directory
import org.gradle.api.provider.Provider
import org.gradle.api.tasks.TaskProvider

@Suppress("UnstableApiUsage")
class OutputDirProvider(private val taskProvider: TaskProvider<ResourceLocatorTask>) {

    fun getOutputDirProperty(): Provider<Directory> {
        return taskProvider.flatMap { it.outputDir }
    }
}