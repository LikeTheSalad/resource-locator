package com.likethesalad.tools.resource.locator.android.extension

import com.likethesalad.tools.resource.api.android.environment.Language
import com.likethesalad.tools.resource.api.collection.ResourceCollection
import com.likethesalad.tools.resource.locator.android.merger.LanguageResourcesMerger
import com.likethesalad.tools.resource.locator.android.utils.CollectedFilesHelper
import com.likethesalad.tools.resource.serializer.ResourceSerializer
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import java.io.File
import java.io.FileNotFoundException

class LanguageResourceFinder @AssistedInject constructor(
    @Assisted private val dir: File,
    private val serializer: ResourceSerializer
) {

    @AssistedFactory
    interface Factory {
        fun create(dir: File): LanguageResourceFinder
    }

    private var defaultResources: ResourceCollection? = null

    fun listLanguages(): List<Language> {
        val files = dir.listFiles { _, name ->
            CollectedFilesHelper.isResourceFileName(name)
        }?.filter { it.isFile }?.toList() ?: emptyList()

        return files.map { CollectedFilesHelper.getLanguageFromFileName(it.name) }
    }

    fun getMergedResourcesForLanguage(language: Language): ResourceCollection {
        var resources = when (language) {
            Language.Default -> getDefaultResourceCollection()
            else -> getResources(language)
        } ?: throw IllegalArgumentException("No resources found for language: '${language.id}'")

        if (language != Language.Default) {
            resources = mergeResourcesWithDefaults(resources, language)
        }

        return resources
    }

    private fun mergeResourcesWithDefaults(resources: ResourceCollection, language: Language): ResourceCollection {
        return getDefaultResourceCollection()?.let { defaultResources ->
            val merger = LanguageResourcesMerger(language)
            merger.merge(listOf(resources, defaultResources))
        } ?: resources
    }

    private fun getDefaultResourceCollection(): ResourceCollection? {
        if (defaultResources == null) {
            defaultResources = getResources(Language.Default)
        }

        return defaultResources
    }

    private fun getResources(language: Language): ResourceCollection? {
        val resourceFile = File(dir, CollectedFilesHelper.getResourceFileName(language))
        return try {
            serializer.deserializeCollection(resourceFile.readText())
        } catch (e: FileNotFoundException) {
            null
        }
    }
}