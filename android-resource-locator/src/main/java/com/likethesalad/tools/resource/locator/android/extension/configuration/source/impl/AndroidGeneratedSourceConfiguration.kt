package com.likethesalad.tools.resource.locator.android.extension.configuration.source.impl

import com.likethesalad.tools.resource.api.android.environment.Variant
import com.likethesalad.tools.resource.collector.android.data.resdir.ResDir
import com.likethesalad.tools.resource.collector.android.data.variant.VariantTree
import com.likethesalad.tools.resource.locator.android.extension.configuration.source.base.ResDirResourceSourceConfiguration
import com.likethesalad.tools.resource.locator.android.providers.TaskFinder
import org.gradle.api.provider.Provider
import java.io.File

class AndroidGeneratedSourceConfiguration(
    variantTree: VariantTree,
    private val taskFinder: TaskFinder
) : ResDirResourceSourceConfiguration(variantTree) {

    private lateinit var generatedFiles: Provider<Iterable<File>>

    override fun getResDirs(): List<ResDir> {
        return listOf(ResDir(Variant.Default, generatedFiles.get().iterator().next()))
    }

    override fun getSourceFiles(): Provider<Iterable<File>> {
        generatedFiles = getAndroidGenerateResourcesTask()
        return generatedFiles
    }

    private fun getAndroidGenerateResourcesTask(): Provider<Iterable<File>> {
        val variantName = variantTree.androidVariantData.getVariantName()
        return taskFinder.findTaskOutputsByName("generate${variantName.capitalize()}ResValues")
    }
}