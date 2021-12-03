package com.likethesalad.tools.resource.locator.android

import com.likethesalad.tools.resource.api.android.AndroidResource
import com.likethesalad.tools.resource.api.android.AndroidResourceScope
import com.likethesalad.tools.resource.api.android.environment.Language
import com.likethesalad.tools.resource.api.android.environment.Variant
import com.likethesalad.tools.resource.api.collection.BasicResourceCollection
import com.likethesalad.tools.resource.api.collection.ResourceCollection
import com.likethesalad.tools.resource.collector.ResourceCollector
import com.likethesalad.tools.resource.collector.android.AndroidResourceCollector
import com.likethesalad.tools.resource.collector.android.data.resdir.ResDir
import com.likethesalad.tools.resource.collector.android.source.providers.ResDirResourceSourceProvider
import com.likethesalad.tools.resource.locator.android.utils.CollectedFilesHelper
import com.likethesalad.tools.resource.serializer.ResourceSerializer
import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.file.FileCollection
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import java.io.File
import javax.inject.Inject

open class ResourceLocatorTask @Inject constructor(
    private val collector: ResourceCollector,
    private val serializer: ResourceSerializer,
    providedOutputDir: DirectoryProperty
) : DefaultTask() {

    @InputFiles
    lateinit var androidGeneratedResDirs: FileCollection

    @InputFiles
    lateinit var libraryResources: FileCollection

    @InputFiles
    lateinit var rawFiles: FileCollection

    @OutputDirectory
    val outputDir: DirectoryProperty = providedOutputDir

    @TaskAction
    fun runTask() {
        clearOutputDir()
        addExtraResourceSources()

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

    private fun addExtraResourceSources() {
        val androidResourceCollector = collector as AndroidResourceCollector
        addGeneratedResources(androidResourceCollector)
        addLibrariesResources(androidResourceCollector)
    }

    private fun addLibrariesResources(androidResourceCollector: AndroidResourceCollector) {
        if (libraryResources.isEmpty) {
            return
        }
        val provider =
            ResDirResourceSourceProvider.createInstance(ResDir(Variant.Dependency, libraryResources.singleFile))
        androidResourceCollector.getComposableSourceProvider().addProvider(provider)
    }

    private fun addGeneratedResources(androidResourceCollector: AndroidResourceCollector) {
        val provider =
            ResDirResourceSourceProvider.createInstance(ResDir(Variant.Default, androidGeneratedResDirs.singleFile))
        androidResourceCollector.getComposableSourceProvider().addProvider(provider)
    }

    private fun saveCollection(language: Language, collection: ResourceCollection) {
        createOutputFileForLanguage(language).writeText(serializer.serializeCollection(collection))
    }

    private fun createOutputFileForLanguage(language: Language): File {
        val dir = outputDir.get().asFile
        return File(dir, CollectedFilesHelper.getResourceFileName(language))
    }
}