package com.likethesalad.tools.resource.locator.android

import com.likethesalad.tools.resource.api.android.environment.Language
import com.likethesalad.tools.resource.api.collection.ResourceCollection
import java.io.File

class LanguageResourceCollection(private val dir: File) {

    companion object {
        const val COLLECTED_DIR_BASE_NAME = "resources"
        private val resourceFilePattern = Regex("$COLLECTED_DIR_BASE_NAME(-([^\\s\\t]+))*\\.json")
    }

    fun listLanguages(): List<Language> {
        val directories = dir.listFiles()?.filter { it.isDirectory }?.toList() ?: emptyList()

    }

    fun getResourcesForLanguage(language: Language): ResourceCollection {
        TODO()
    }
}