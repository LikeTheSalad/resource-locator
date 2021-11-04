package com.likethesalad.tools.resource.locator.android.merger

import com.likethesalad.tools.resource.api.android.AndroidResource
import com.likethesalad.tools.resource.api.android.AndroidResourceScope
import com.likethesalad.tools.resource.api.android.environment.Language
import com.likethesalad.tools.resource.api.collection.BasicResourceCollection
import com.likethesalad.tools.resource.api.collection.ResourceCollection
import com.likethesalad.tools.resource.collector.merger.ResourceMerger

class LanguageResourcesMerger(private val language: Language) : ResourceMerger {

    private val resourcesMap = mutableMapOf<String, AndroidResource>()

    override fun merge(collections: List<ResourceCollection>): ResourceCollection {
        for (collection in collections) {
            for (resource in collection.getAllResources()) {
                addResource(resource as AndroidResource)
            }
        }

        val resources = resourcesMap.values.toList().sortedBy { it.name() }
        resourcesMap.clear()
        return BasicResourceCollection(resources)
    }

    private fun addResource(resource: AndroidResource) {
        val resourceId = "${resource.name()}__${resource.type().getName()}"
        if (resourcesMap.containsKey(resourceId)) {
            resolveConflict(resourceId, resource)
        } else {
            resourcesMap[resourceId] = resource
        }
    }

    private fun resolveConflict(resourceId: String, newResource: AndroidResource) {
        val newResourceScope = newResource.scope() as AndroidResourceScope
        if (newResourceScope.language == language) {
            resourcesMap[resourceId] = newResource
        }
    }
}