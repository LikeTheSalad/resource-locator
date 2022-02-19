package com.likethesalad.tools.resource.locator.android.extension.configuration.data

import com.likethesalad.tools.resource.locator.android.extension.resources.LanguageCollectionProvider
import com.likethesalad.tools.resource.locator.android.extension.resources.LanguageResourcesHandler

class ResourcesProvider(languageCollectionProvider: LanguageCollectionProvider) {

    val resources: LanguageResourcesHandler by lazy {
        LanguageResourcesHandler(languageCollectionProvider)
    }
}
