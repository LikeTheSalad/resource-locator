package com.likethesalad.tools.resource.locator.android.extension.resources

import com.likethesalad.tools.resource.api.android.environment.Language
import com.likethesalad.tools.resource.api.collection.ResourceCollection
import com.likethesalad.tools.resource.locator.android.merger.LanguageResourcesMerger

class LanguageResourcesHandler(
    private val languageCollectionProvider: LanguageCollectionProvider
) {
    private var defaultResources: ResourceCollection? = null

    fun listLanguages(): List<Language> {
        return languageCollectionProvider.listLanguages()
    }

    fun getMergedResourcesForLanguage(language: Language): ResourceCollection {
        var resources = when (language) {
            Language.Default -> getDefaultResourceCollection()
            else -> languageCollectionProvider.getCollectionByLanguage(language)
        } ?: return getDefaultResourceCollection() ?: throw UnsupportedOperationException("No resources available")

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
            defaultResources = languageCollectionProvider.getCollectionByLanguage(Language.Default)
        }

        return defaultResources
    }
}