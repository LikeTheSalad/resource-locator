package com.likethesalad.tools.resource.locator.android

import com.android.build.gradle.AppExtension
import com.android.build.gradle.api.ApplicationVariant
import com.likethesalad.tools.android.plugin.impl.DefaultAndroidVariantData
import com.likethesalad.tools.resource.collector.ResourceCollector
import com.likethesalad.tools.resource.collector.android.data.variant.VariantTree
import com.likethesalad.tools.resource.locator.android.di.DaggerResourceLocatorComponent
import com.likethesalad.tools.resource.locator.android.di.ResourceLocatorComponent
import com.likethesalad.tools.resource.locator.android.di.ResourceLocatorModule
import com.likethesalad.tools.resource.locator.android.extension.ResourceLocatorExtension
import com.likethesalad.tools.resource.locator.android.extension.observer.ResourceLocatorTaskPublisher
import com.likethesalad.tools.resource.locator.android.extension.observer.data.ResourceLocatorTaskContainer
import com.likethesalad.tools.resource.locator.android.extension.observer.data.ResourceLocatorTaskContext
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.TaskProvider
import java.io.File

abstract class AndroidResourceLocatorPlugin : Plugin<Project> {

    private lateinit var project: Project
    private val taskPublisher: ResourceLocatorTaskPublisher by lazy { ResourceLocatorTaskPublisher() }

    override fun apply(project: Project) {
        val android = project.extensions.getByType(AppExtension::class.java)
        this.project = project
        val component = getResourceLocatorComponent()
        createExtension(project, component)

        project.afterEvaluate {
            android.applicationVariants.forEach { variant ->
                createResourceLocatorTaskForVariant(variant)
            }
        }
    }

    private fun createExtension(
        project: Project,
        component: ResourceLocatorComponent
    ) {
        project.extensions.create(
            "${getLocatorId()}ResourceLocator", ResourceLocatorExtension::class.java,
            taskPublisher,
            component.languageResourceFinderFactory()
        )
    }

    private fun createResourceLocatorTaskForVariant(
        androidVariant: ApplicationVariant
    ) {
        val taskName = "${getLocatorId()}${androidVariant.name.toString().capitalize()}ResourceLocator"
        val outputDir = getOutputDirForTaskName(taskName)
        val variantTree = VariantTree(DefaultAndroidVariantData(androidVariant))
        val collector = getResourceCollector(variantTree)
        val taskProvider = project.tasks.register(taskName, ResourceLocatorTask::class.java, collector, variantTree)

        configureTask(taskProvider, outputDir)

        notifyTaskCreated(variantTree, taskProvider, outputDir)
    }

    private fun notifyTaskCreated(
        variantTree: VariantTree,
        taskProvider: TaskProvider<ResourceLocatorTask>,
        outputDir: File
    ) {
        val taskContext = ResourceLocatorTaskContext(variantTree)
        val taskContainer = ResourceLocatorTaskContainer(taskProvider, outputDir, taskContext)
        taskPublisher.notify(taskContainer)
    }

    private fun configureTask(
        taskProvider: TaskProvider<ResourceLocatorTask>,
        outputDir: File
    ) {
        taskProvider.configure { task ->
            task.outputDir = outputDir
        }
    }

    private fun getOutputDirForTaskName(taskName: String): File {
        return File("${project.buildDir}/intermediates/incremental/$taskName")
    }

    private fun getResourceLocatorComponent(): ResourceLocatorComponent {
        return DaggerResourceLocatorComponent.builder().resourceLocatorModule(ResourceLocatorModule()).build()
    }

    abstract fun getLocatorId(): String

    abstract fun getResourceCollector(variantTree: VariantTree): ResourceCollector
}