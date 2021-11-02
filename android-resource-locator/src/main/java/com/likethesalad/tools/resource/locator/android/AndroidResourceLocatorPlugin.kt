package com.likethesalad.tools.resource.locator.android

import com.likethesalad.resource.serializer.android.AndroidResourceSerializer
import com.likethesalad.tools.android.plugin.AndroidToolsPlugin
import com.likethesalad.tools.android.plugin.data.AndroidExtension
import com.likethesalad.tools.android.plugin.data.AndroidVariantData
import com.likethesalad.tools.android.plugin.extension.AndroidToolsPluginExtension
import com.likethesalad.tools.resource.collector.ResourceCollector
import com.likethesalad.tools.resource.collector.android.data.variant.VariantTree
import com.likethesalad.tools.resource.locator.android.di.DaggerResourceLocatorComponent
import com.likethesalad.tools.resource.locator.android.di.ResourceLocatorComponent
import com.likethesalad.tools.resource.locator.android.di.ResourceLocatorModule
import com.likethesalad.tools.resource.locator.android.extension.ResourceLocatorExtension
import com.likethesalad.tools.resource.locator.android.extension.observer.ResourceLocatorTaskPublisher
import com.likethesalad.tools.resource.locator.android.extension.observer.data.ResourceLocatorTaskContainer
import com.likethesalad.tools.resource.locator.android.extension.observer.data.ResourceLocatorTaskContext
import com.likethesalad.tools.resource.locator.android.utils.AndroidResourcesHelper
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.file.FileCollection
import org.gradle.api.tasks.TaskProvider

abstract class AndroidResourceLocatorPlugin : Plugin<Project> {

    private lateinit var project: Project
    private lateinit var androidExtension: AndroidExtension
    private val serializer by lazy { AndroidResourceSerializer() }
    private val taskPublisher: ResourceLocatorTaskPublisher by lazy {
        ResourceLocatorTaskPublisher()
    }

    override fun apply(project: Project) {
        this.project = project
        val androidToolsPluginExtension = findAndroidToolsPluginExtension()
        androidExtension = androidToolsPluginExtension.androidExtension
        val component = getResourceLocatorComponent()
        createExtension(project, component)

        androidToolsPluginExtension.onVariant { variant ->
            createResourceLocatorTaskForVariant(variant)
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
        project: Project,
        component: ResourceLocatorComponent
    ) {
        project.extensions.create(
            "${getLocatorId()}ResourceLocator", ResourceLocatorExtension::class.java,
            taskPublisher,
            component.languageResourceFinderFactory(),
            serializer
        )
    }

    private fun createResourceLocatorTaskForVariant(
        androidVariant: AndroidVariantData
    ) {
        val taskName = "${getLocatorId()}${androidVariant.getVariantName().capitalize()}ResourceLocator"
        val variantTree = VariantTree(androidVariant)
        val collector = getResourceCollector(variantTree)
        val taskProvider = project.tasks.register(taskName, ResourceLocatorTask::class.java, collector, serializer)
        val outputDir = getOutputDirForTaskName(taskProvider)

        configureTask(androidVariant, taskProvider, outputDir)

        notifyTaskCreated(variantTree, outputDir)
    }

    private fun notifyTaskCreated(
        variantTree: VariantTree,
        outputDir: FileCollection
    ) {
        val taskContext = ResourceLocatorTaskContext(variantTree)
        val taskContainer = ResourceLocatorTaskContainer(outputDir, taskContext)
        taskPublisher.notify(taskContainer)
    }

    private fun configureTask(
        variantData: AndroidVariantData,
        taskProvider: TaskProvider<ResourceLocatorTask>,
        outputDir: FileCollection
    ) {
        taskProvider.configure { task ->
            task.androidGeneratedResDirs =
                getAndroidGenerateResourcesTask(variantData.getVariantName()).get().outputs.files
            task.libraryResources = AndroidResourcesHelper.getLibrariesResourceFileCollection(variantData)
            task.outputDir = outputDir
        }
    }

    private fun getOutputDirForTaskName(taskProvider: TaskProvider<ResourceLocatorTask>): FileCollection {
        val fileCollection = project.files("${project.buildDir}/intermediates/incremental/${taskProvider.name}")
        fileCollection.builtBy(taskProvider)
        return fileCollection
    }

    private fun getResourceLocatorComponent(): ResourceLocatorComponent {
        return DaggerResourceLocatorComponent.builder().resourceLocatorModule(ResourceLocatorModule()).build()
    }

    private fun getAndroidGenerateResourcesTask(variantName: String): TaskProvider<Task> {
        return project.tasks.named("generate${variantName.capitalize()}ResValues")
    }

    protected fun getAndroidExtension(): AndroidExtension {
        return androidExtension
    }

    abstract fun getLocatorId(): String

    abstract fun getResourceCollector(
        variantTree: VariantTree
    ): ResourceCollector
}