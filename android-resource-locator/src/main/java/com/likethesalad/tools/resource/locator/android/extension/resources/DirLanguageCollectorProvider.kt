package com.likethesalad.tools.resource.locator.android.extension.resources

import com.likethesalad.tools.resource.api.android.environment.Language
import com.likethesalad.tools.resource.api.collection.ResourceCollection
import com.likethesalad.tools.resource.locator.android.providers.FileProvider
import com.likethesalad.tools.resource.locator.android.utils.CollectedFilesHelper
import com.likethesalad.tools.resource.serializer.ResourceSerializer
import java.io.File
import java.io.FileNotFoundException

class DirLanguageCollectorProvider constructor(
    private val serializer: ResourceSerializer,
    dirProvider: FileProvider
) : LanguageCollectionProvider {

    private val dir by lazy { dirProvider.getFile() }

    override fun getCollectionByLanguage(language: Language): ResourceCollection? {
        val resourceFile = File(dir, CollectedFilesHelper.getResourceFileName(language))
        return try {
            serializer.deserializeCollection(resourceFile.readText())
        } catch (e: FileNotFoundException) {
            null
        }
    }

    override fun listLanguages(): List<Language> {
        val files = dir.listFiles { _, name ->
            CollectedFilesHelper.isResourceFileName(name)
        }?.filter { it.isFile }?.toList() ?: emptyList()

        return files.map { CollectedFilesHelper.getLanguageFromFileName(it.name) }
    }
}