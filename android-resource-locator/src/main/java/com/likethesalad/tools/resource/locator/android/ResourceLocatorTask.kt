package com.likethesalad.tools.resource.locator.android

import com.likethesalad.tools.resource.collector.ResourceCollector
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import java.io.File

open class ResourceLocatorTask(
    private val collector: ResourceCollector
) : DefaultTask() {

    @OutputDirectory
    lateinit var outputDir: File

    @TaskAction
    fun runTask() {
        collector.collect()
    }
}