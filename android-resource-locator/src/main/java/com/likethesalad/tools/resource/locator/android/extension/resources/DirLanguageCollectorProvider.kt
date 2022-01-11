package com.likethesalad.tools.resource.locator.android.extension.resources

import com.likethesalad.tools.resource.api.android.environment.Language
import com.likethesalad.tools.resource.api.collection.ResourceCollection
import com.likethesalad.tools.resource.locator.android.utils.CollectedFilesHelper
import com.likethesalad.tools.resource.serializer.ResourceSerializer
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import java.io.File
import java.io.FileNotFoundException

class DirLanguageCollectorProvider @AssistedInject constructor(
    @Assisted private val dir: File,
    private val serializer: ResourceSerializer
) : LanguageCollectionProvider {

    @AssistedFactory
    interface Factory {
        fun create(dir: File): DirLanguageCollectorProvider
    }

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