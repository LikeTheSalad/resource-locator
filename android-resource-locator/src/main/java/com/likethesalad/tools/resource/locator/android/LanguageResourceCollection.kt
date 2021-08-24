package com.likethesalad.tools.resource.locator.android

import com.likethesalad.tools.resource.api.android.environment.Language
import com.likethesalad.tools.resource.api.collection.ResourceCollection
import java.io.File

class LanguageResourceCollection(private val dir: File) {


    fun listLanguages(): List<Language> {
        val files = dir.listFiles { _, name ->
            CollectedFilesHelper.isResourceFileName(name)
        }?.filter { it.isFile }?.toList() ?: emptyList()

        return files.map { CollectedFilesHelper.getLanguageFromFileName(it.name) }
    }

    fun getResourcesForLanguage(language: Language): ResourceCollection {
        TODO()
    }
}