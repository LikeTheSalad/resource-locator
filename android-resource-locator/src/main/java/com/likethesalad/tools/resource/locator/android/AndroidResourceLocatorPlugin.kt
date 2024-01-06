package com.likethesalad.tools.resource.locator.android

import com.likethesalad.plugins.agpbrigde.base.AndroidBridgePluginConsumer
import com.likethesalad.resource.serializer.android.AndroidResourceSerializer
import com.likethesalad.tools.agpcompat.api.bridges.AndroidExtension
import com.likethesalad.tools.agpcompat.api.bridges.AndroidVariantData
import com.likethesalad.tools.resource.collector.android.AndroidResourceCollector
import com.likethesalad.tools.resource.collector.android.data.variant.VariantTree
import com.likethesalad.tools.resource.collector.android.di.CollectorComponent
import com.likethesalad.tools.resource.collector.android.di.CollectorModule
import com.likethesalad.tools.resource.collector.android.di.DaggerCollectorComponent
import com.likethesalad.tools.resource.locator.android.di.ResourceLocatorComponent
import com.likethesalad.tools.resource.locator.android.di.ResourceLocatorComponentProvider
import com.likethesalad.tools.resource.locator.android.extension.AndroidResourceLocatorExtension
import com.likethesalad.tools.resource.locator.android.extension.configuration.data.DirectoryFileProvider
import com.likethesalad.tools.resource.locator.android.extension.configuration.data.OutputDirProvider
import com.likethesalad.tools.resource.locator.android.extension.configuration.data.ResourceLocatorInfo
import com.likethesalad.tools.resource.locator.android.extension.configuration.data.ResourcesProvider
import com.likethesalad.tools.resource.locator.android.extension.configuration.data.TaskInfo
import com.likethesalad.tools.resource.locator.android.extension.data.ResourceLocatorRequest
import com.likethesalad.tools.resource.locator.android.extension.resources.DirLanguageCollectorProvider
import com.likethesalad.tools.resource.locator.android.providers.InstancesProvider
import com.likethesalad.tools.resource.locator.android.providers.TaskFinder
import com.likethesalad.tools.resource.locator.android.task.ResourceLocatorTask
import org.gradle.api.Project
import org.gradle.api.file.Directory
import org.gradle.api.provider.Provider
import java.io.File
import java.util.concurrent.Callable

abstract class AndroidResourceLocatorPlugin : AndroidBridgePluginConsumer(), TaskFinder, InstancesProvider {

    private lateinit var project: Project
    private val serializer by lazy { AndroidResourceSerializer() }
    internal lateinit var androidExtension: AndroidExtension

    override fun apply(project: Project) {
        super.apply(project)
        this.project = project
        androidExtension = androidBridge.androidExtension
        ResourceLocatorComponentProvider.init(this)
        val component = ResourceLocatorComponentProvider.getComponent()
        val resourceLocatorExtension = createExtension(project, component)

        androidBridge.onVariant { variant ->
            createResourceLocatorTasksForVariant(variant, resourceLocatorExtension)
        }
    }

    private fun createExtension(
        project: Project,
        component: ResourceLocatorComponent
    ): AndroidResourceLocatorExtension {
        return project.extensions.create(
            "${getLocatorId()}ResourceLocator",
            AndroidResourceLocatorExtension::class.java,
            component.commonSourceConfigurationCreator(),
            serializer
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
            val entryPoint = request.entryPoint
            val taskProvider = project.tasks.register(
                taskName,
                ResourceLocatorTask::class.java,
                collector,
                serializer,
                outputDir,
                entryPoint,
                variantTree
            )

            val taskInfo = TaskInfo(taskName, OutputDirProvider(taskProvider))
            val languageCollectionProvider = DirLanguageCollectorProvider(serializer, DirectoryFileProvider(outputDir))
            val info = ResourceLocatorInfo(taskInfo, ResourcesProvider(languageCollectionProvider))
            request.listener?.onLocatorReady(request.name, variantTree, info)
        }
    }

    private fun createTaskName(request: ResourceLocatorRequest, androidVariant: AndroidVariantData): String {
        val locatorId = getLocatorId().capitalize()
        val variantName = androidVariant.getVariantName().capitalize()
        return "${request.name}${locatorId}${variantName}ResourceLocator"
    }

    private fun getOutputDirForTaskName(name: String): Provider<Directory> {
        return project.layout.buildDirectory.dir("intermediates/incremental/$name")
    }

    override fun findTaskOutputsByName(name: String): Provider<Iterable<File>> {
        return project.provider {
            project.tasks.getByName(name).outputs.files
        }
    }

    override fun <T> createProvider(callable: Callable<T>): Provider<T> {
        return project.provider(callable)
    }

    internal fun getCollectorComponent(): CollectorComponent {
        return DaggerCollectorComponent.builder()
            .collectorModule(CollectorModule(androidExtension))
            .build()
    }

    abstract fun getLocatorId(): String

    abstract fun getResourceCollector(
        variantTree: VariantTree
    ): AndroidResourceCollector
}