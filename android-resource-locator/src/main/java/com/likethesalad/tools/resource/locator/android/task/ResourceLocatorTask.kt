package com.likethesalad.tools.resource.locator.android.task

import com.likethesalad.tools.resource.api.android.AndroidResource
import com.likethesalad.tools.resource.api.android.AndroidResourceScope
import com.likethesalad.tools.resource.api.android.environment.Language
import com.likethesalad.tools.resource.api.collection.BasicResourceCollection
import com.likethesalad.tools.resource.api.collection.ResourceCollection
import com.likethesalad.tools.resource.collector.ResourceCollector
import com.likethesalad.tools.resource.collector.android.AndroidResourceCollector
import com.likethesalad.tools.resource.collector.android.data.variant.VariantTree
import com.likethesalad.tools.resource.collector.android.source.providers.ComposableResourceSourceProvider
import com.likethesalad.tools.resource.collector.source.ResourceSourceProvider
import com.likethesalad.tools.resource.locator.android.extension.configuration.ResourceLocatorEntryPoint
import com.likethesalad.tools.resource.locator.android.utils.CollectedFilesHelper
import com.likethesalad.tools.resource.serializer.ResourceSerializer
import org.gradle.api.DefaultTask
import org.gradle.api.file.Directory
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.Provider
import org.gradle.api.provider.SetProperty
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import java.io.File
import javax.inject.Inject

@Suppress("UnstableApiUsage", "UNCHECKED_CAST")
open class ResourceLocatorTask @Inject constructor(
    private val collector: ResourceCollector,
    private val serializer: ResourceSerializer,
    outputDirProvider: Provider<Directory>,
    entryPoint: ResourceLocatorEntryPoint,
    variantTree: VariantTree
) : DefaultTask() {

    @Internal
    private val sourceConfigurations = entryPoint.getResourceSourceConfigurations(variantTree)

    @InputFiles
    val inputResources: SetProperty<Iterable<File>> =
        project.objects.setProperty(Iterable::class.java) as SetProperty<Iterable<File>>

    @OutputDirectory
    val outputDir: DirectoryProperty = project.objects.directoryProperty()

    init {
        sourceConfigurations.forEach { sources ->
            inputResources.add(sources.getSourceFiles())
        }
        outputDir.set(outputDirProvider)
    }

    @TaskAction
    fun runTask() {
        clearOutputDir()
        addResourceSources()

        val collection = collector.collect()
        val collectionsByLanguage = collection.getAllResources()
            .sortedBy { (it as AndroidResource).name() }
            .groupBy { (it.scope() as AndroidResourceScope).language }
            .mapValues { BasicResourceCollection(it.value) }

        collectionsByLanguage.forEach { (language, collection) ->
            saveCollection(language, collection)
        }
    }

    private fun clearOutputDir() {
        val dir = outputDir.get().asFile
        if (dir.exists()) {
            dir.listFiles()?.forEach { it.deleteRecursively() }
        }
    }

    private fun addResourceSources() {
        val composableSources = (collector as AndroidResourceCollector).getComposableSourceProvider()
        sourceConfigurations.forEach { source ->
            addProviders(source.getSourceProviders(), composableSources)
        }
    }

    private fun addProviders(
        sourceProviders: List<ResourceSourceProvider>,
        composableSources: ComposableResourceSourceProvider
    ) {
        sourceProviders.forEach { provider ->
            composableSources.addProvider(provider)
        }
    }

    private fun saveCollection(language: Language, collection: ResourceCollection) {
        createOutputFileForLanguage(language).writeText(serializer.serializeCollection(collection))
    }

    private fun createOutputFileForLanguage(language: Language): File {
        val dir = outputDir.get().asFile
        return File(dir, CollectedFilesHelper.getResourceFileName(language))
    }
}