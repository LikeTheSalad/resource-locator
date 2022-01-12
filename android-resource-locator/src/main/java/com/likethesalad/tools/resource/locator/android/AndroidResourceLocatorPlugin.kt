package com.likethesalad.tools.resource.locator.android

import com.likethesalad.resource.serializer.android.AndroidResourceSerializer
import com.likethesalad.tools.android.plugin.AndroidToolsPlugin
import com.likethesalad.tools.android.plugin.data.AndroidExtension
import com.likethesalad.tools.android.plugin.data.AndroidVariantData
import com.likethesalad.tools.android.plugin.extension.AndroidToolsPluginExtension
import com.likethesalad.tools.resource.collector.ResourceCollector
import com.likethesalad.tools.resource.collector.android.AndroidResourceCollector
import com.likethesalad.tools.resource.collector.android.data.variant.VariantTree
import com.likethesalad.tools.resource.collector.android.di.CollectorComponentProvider
import com.likethesalad.tools.resource.locator.android.extension.AndroidResourceLocatorExtension
import com.likethesalad.tools.resource.locator.android.extension.data.ResourceLocatorConfiguration
import com.likethesalad.tools.resource.locator.android.extension.data.ResourceLocatorRequest
import com.likethesalad.tools.resource.locator.android.extension.listener.data.OutputDirProvider
import com.likethesalad.tools.resource.locator.android.extension.listener.data.ResourceLocatorInfo
import com.likethesalad.tools.resource.locator.android.utils.AndroidResourcesHelper
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.file.Directory
import org.gradle.api.provider.Provider
import org.gradle.api.tasks.TaskProvider
import java.io.File

abstract class AndroidResourceLocatorPlugin : Plugin<Project> {

    private lateinit var project: Project
    private lateinit var androidExtension: AndroidExtension
    private val serializer by lazy { AndroidResourceSerializer() }

    override fun apply(project: Project) {
        this.project = project
        val androidToolsPluginExtension = findAndroidToolsPluginExtension()
        androidExtension = androidToolsPluginExtension.androidExtension
        CollectorComponentProvider.initialize(androidExtension)
        val resourceLocatorExtension = createExtension(project)

        androidToolsPluginExtension.onVariant { variant ->
            createResourceLocatorTasksForVariant(variant, resourceLocatorExtension)
        }
    }

    private fun findAndroidToolsPluginExtension(): AndroidToolsPluginExtension {
        val toolsPluginExtension = project.extensions.findByType(AndroidToolsPluginExtension::class.java)
        if (toolsPluginExtension == null) {
            project.plugins.apply(AndroidToolsPlugin::class.java)
            return project.extensions.getByType(AndroidToolsPluginExtension::class.java)
        }

        return toolsPluginExtension
    }

    private fun createExtension(
        project: Project
    ): AndroidResourceLocatorExtension {
        return project.extensions.create(
            "${getLocatorId()}ResourceLocator", AndroidResourceLocatorExtension::class.java, serializer
        )
    }

    private fun createResourceLocatorTasksForVariant(
        androidVariant: AndroidVariantData,
        androidResourceLocatorExtension: AndroidResourceLocatorExtension
    ) {
        val variantTree = VariantTree(androidVariant)

        for (request in androidResourceLocatorExtension.getLocatorRequests()) {
            val taskName = createTaskName(request, androidVariant)
            val collector = getResourceCollector(variantTree)
            val outputDir = getOutputDirForTaskName(taskName)
            val taskProvider = project.tasks.register(taskName, ResourceLocatorTask::class.java, collector, serializer)

            configureCollector(variantTree, collector, request.configuration)
            configureTask(androidVariant, taskProvider, collector, outputDir)
            request.listener?.onLocatorReady(
                variantTree,
                ResourceLocatorInfo(taskName, OutputDirProvider(taskProvider))
            )
        }
    }

    private fun configureCollector(
        variantTree: VariantTree,
        collector: AndroidResourceCollector,
        configuration: ResourceLocatorConfiguration
    ) {
        val composableSourceProvider = collector.getComposableSourceProvider()
        configuration.getSourceProviders(variantTree).forEach { sourceProvider ->
            composableSourceProvider.addProvider(sourceProvider)
        }
        composableSourceProvider.addFilterRules(configuration.getSourceFilterRules(variantTree))
    }

    private fun createTaskName(request: ResourceLocatorRequest, androidVariant: AndroidVariantData): String {
        val locatorId = getLocatorId().capitalize()
        val variantName = androidVariant.getVariantName().capitalize()
        return "${request.name}${locatorId}${variantName}ResourceLocator"
    }

    private fun configureTask(
        variantData: AndroidVariantData,
        taskProvider: TaskProvider<ResourceLocatorTask>,
        collector: ResourceCollector,
        outputDir: Provider<Directory>,
    ) {
        taskProvider.configure { task ->
            val sourceFiles = collector.getSourceProvider().getSources().map { it.getSource() as File }
            task.androidGeneratedResDirs =
                getAndroidGenerateResourcesTask(variantData.getVariantName()).get().outputs.files
            task.libraryResources = AndroidResourcesHelper.getLibrariesResourceFileCollection(variantData)
            task.rawFiles = project.files(sourceFiles)
            task.outputDir.set(outputDir)
        }
    }

    private fun getOutputDirForTaskName(name: String): Provider<Directory> {
        return project.layout.buildDirectory.dir("intermediates/incremental/$name")
    }

    private fun getAndroidGenerateResourcesTask(variantName: String): TaskProvider<Task> {
        return project.tasks.named("generate${variantName.capitalize()}ResValues")
    }

    abstract fun getLocatorId(): String

    abstract fun getResourceCollector(
        variantTree: VariantTree
    ): AndroidResourceCollector
}