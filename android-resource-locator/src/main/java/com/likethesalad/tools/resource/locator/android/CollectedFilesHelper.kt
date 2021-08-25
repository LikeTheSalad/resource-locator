package com.likethesalad.tools.resource.locator.android

import com.likethesalad.tools.resource.api.android.environment.Language

object CollectedFilesHelper {

    private const val COLLECTED_RESOURCES_BASE_NAME = "resources"
    private val resourceFilePattern = Regex("$COLLECTED_RESOURCES_BASE_NAME(-([^\\s\\t]+))*\\.json")

    fun isResourceFileName(name: String): Boolean {
        return resourceFilePattern.matches(name)
    }

    fun getResourceFileName(language: Language): String {
        val suffix = when (language) {
            is Language.Default -> ""
            else -> "-${language.id}"
        }

        return "$COLLECTED_RESOURCES_BASE_NAME$suffix.json"
    }

    fun getLanguageFromFileName(fileName: String): Language {
        val matchGroupValues = resourceFilePattern.matchEntire(fileName)?.groupValues!!
        val suffix = matchGroupValues[2]
        return if (suffix.isEmpty()) {
            Language.Default
        } else {
            Language.Custom(suffix)
        }
    }
}