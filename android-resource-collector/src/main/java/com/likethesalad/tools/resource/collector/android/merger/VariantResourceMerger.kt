package com.likethesalad.tools.resource.collector.android.merger

import com.likethesalad.tools.resource.api.Resource
import com.likethesalad.tools.resource.api.android.AndroidResource
import com.likethesalad.tools.resource.api.android.impl.AndroidResourceScope
import com.likethesalad.tools.resource.api.collection.BasicResourceCollection
import com.likethesalad.tools.resource.api.collection.ResourceCollection
import com.likethesalad.tools.resource.collector.android.data.variant.VariantTree
import com.likethesalad.tools.resource.collector.merger.ResourceMerger

class VariantResourceMerger(private val tree: VariantTree) : ResourceMerger {

    private val resourcesMap = mutableMapOf<String, Resource>()

    override fun merge(collections: List<ResourceCollection>): ResourceCollection = synchronized(this) {
        collections.forEach { collection ->
            collection.getAllResources().forEach { resource ->
                addResourceToMap(resource)
            }
        }
        val result = BasicResourceCollection(resourcesMap.values.toList())
        resourcesMap.clear()
        return result
    }

    private fun addResourceToMap(resource: Resource) {
        val androidResource = resource as AndroidResource
        val name = resource.name()
        val scope = resource.scope() as AndroidResourceScope
        val key = "${name}__${scope.language.id}"
        if (key in resourcesMap) {
            resolveVariantConflictAndSaveToMap(key, androidResource)
        } else {
            resourcesMap[key] = resource
        }
    }

    private fun resolveVariantConflictAndSaveToMap(key: String, newResource: AndroidResource) {
        val existingResource = resourcesMap.getValue(key)
        val existingScope = existingResource.scope() as AndroidResourceScope
        val newScope = newResource.scope() as AndroidResourceScope

        checkResourceNameUniqueness(existingScope, newScope, newResource)

        if (tree.check(newScope.variant).isChildOf(existingScope.variant)) {
            resourcesMap[key] = newResource
        }
    }

    private fun checkResourceNameUniqueness(
        existingScope: AndroidResourceScope,
        newScope: AndroidResourceScope,
        newResource: AndroidResource
    ) {
        if (existingScope == newScope) {
            throw IllegalStateException(
                "Found resources with the same name '${newResource.name()}' " +
                        "within the same variant '${newScope.variant.name}' for the same language " +
                        "'${newScope.language.id}'. " +
                        "Resource names must be unique within a single" +
                        "variant-language environment"
            )
        }
    }
}