package com.likethesalad.tools.resource.locator.android.extension.configuration.source.impl

import com.likethesalad.tools.resource.api.android.environment.Variant
import com.likethesalad.tools.resource.collector.android.data.resdir.ResDir
import com.likethesalad.tools.resource.collector.android.data.variant.VariantTree
import com.likethesalad.tools.resource.collector.android.source.providers.ResDirResourceSourceProvider
import com.likethesalad.tools.resource.collector.source.ResourceSourceProvider
import com.likethesalad.tools.resource.locator.android.extension.configuration.source.ResourceSourceConfiguration
import com.likethesalad.tools.resource.locator.android.providers.TaskFinder
import org.gradle.api.Task
import org.gradle.api.file.FileCollection
import org.gradle.api.tasks.TaskProvider
import java.io.File

class AndroidGeneratedSourceConfiguration(
    variantTree: VariantTree,
    private val taskFinder: TaskFinder,
    private val resDirSourceProviderFactory: ResDirResourceSourceProvider.Factory
) :
    ResourceSourceConfiguration(variantTree) {

    private lateinit var generatedFiles: FileCollection

    override fun getSourceProviders(): List<ResourceSourceProvider> {
        return listOf(resDirSourceProviderFactory.create(ResDir(Variant.Default, generatedFiles.singleFile)))
    }

    override fun getSourceFiles(): Iterable<File> {
        generatedFiles = getAndroidGenerateResourcesTask().get().outputs.files
        return generatedFiles
    }

    private fun getAndroidGenerateResourcesTask(): TaskProvider<Task> {
        val variantName = variantTree.androidVariantData.getVariantName()
        return taskFinder.findTaskByName("generate${variantName.capitalize()}ResValues")
    }
}