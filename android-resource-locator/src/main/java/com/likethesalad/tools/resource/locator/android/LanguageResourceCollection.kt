package com.likethesalad.tools.resource.locator.android

import com.likethesalad.tools.resource.api.android.environment.Language
import com.likethesalad.tools.resource.api.collection.ResourceCollection

interface LanguageResourceCollection {
    fun listLanguages(): List<Language>
    fun getResourcesForLanguage(language: Language): ResourceCollection
}