package com.likethesalad.tools.resource.locator.android.extension.resources

import com.likethesalad.tools.resource.api.android.environment.Language
import com.likethesalad.tools.resource.api.collection.ResourceCollection

interface LanguageCollectionProvider {
    fun getCollectionByLanguage(language: Language): ResourceCollection?
    fun listLanguages(): List<Language>
}