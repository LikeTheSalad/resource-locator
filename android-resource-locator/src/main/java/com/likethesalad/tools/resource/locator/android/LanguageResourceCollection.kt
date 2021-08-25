package com.likethesalad.tools.resource.locator.android

import com.likethesalad.tools.resource.api.android.environment.Language
import com.likethesalad.tools.resource.api.collection.ResourceCollection
import com.likethesalad.tools.resource.serializer.ResourceSerializer
import java.io.File
import java.io.FileNotFoundException

class LanguageResourceCollection(
    private val dir: File,
    private val serializer: ResourceSerializer
) {


    fun listLanguages(): List<Language> {
        val files = dir.listFiles { _, name ->
            CollectedFilesHelper.isResourceFileName(name)
        }?.filter { it.isFile }?.toList() ?: emptyList()

        return files.map { CollectedFilesHelper.getLanguageFromFileName(it.name) }
    }

    fun getResourcesForLanguage(language: Language): ResourceCollection {
        val resourceFile = File(dir, CollectedFilesHelper.getResourceFileName(language))
        try {
            return serializer.deserializeCollection(resourceFile.readText())
        } catch (e: FileNotFoundException) {
            throw IllegalArgumentException("No resources found for language: '${language.id}'")
        }
    }
}