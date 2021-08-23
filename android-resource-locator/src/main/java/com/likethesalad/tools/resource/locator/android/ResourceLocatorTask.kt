package com.likethesalad.tools.resource.locator.android

import com.likethesalad.tools.resource.api.android.AndroidResourceScope
import com.likethesalad.tools.resource.api.android.environment.Language
import com.likethesalad.tools.resource.api.collection.BasicResourceCollection
import com.likethesalad.tools.resource.api.collection.ResourceCollection
import com.likethesalad.tools.resource.collector.ResourceCollector
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import java.io.File

open class ResourceLocatorTask(private val collector: ResourceCollector) : DefaultTask() {

    @OutputDirectory
    lateinit var outputDir: File

    @TaskAction
    fun runTask() {
        val collection = collector.collect()
        val collectionsByLanguage = collection.getAllResources()
            .groupBy { (it.scope() as AndroidResourceScope).language }
            .mapValues { BasicResourceCollection(it.value) }

        collectionsByLanguage.forEach { (language, collection) ->
            saveCollection(language, collection)
        }
    }

    private fun saveCollection(language: Language, collection: ResourceCollection) {
        TODO("Not yet implemented")
    }
}