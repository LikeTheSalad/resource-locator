package com.likethesalad.tools.resource.collector.android.merger

import com.likethesalad.tools.resource.api.Resource
import com.likethesalad.tools.resource.api.android.AndroidResource
import com.likethesalad.tools.resource.api.android.AndroidResourceScope
import com.likethesalad.tools.resource.api.collection.BasicResourceCollection
import com.likethesalad.tools.resource.api.collection.ResourceCollection
import com.likethesalad.tools.resource.collector.android.data.variant.VariantTree
import com.likethesalad.tools.resource.collector.merger.ResourceMerger

class VariantResourceMerger(private val tree: VariantTree) : ResourceMerger {

    private val resourcesMap = mutableMapOf<String, Resource>()

    override fun merge(collections: List<ResourceCollection>): ResourceCollection {
        collections.forEach { collection ->
            collection.getAllResources().forEach { resource ->
                addResourceToMap(resource)
            }
        }
        return BasicResourceCollection(resourcesMap.values.toList())
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
        val existingVariant = (existingResource.scope() as AndroidResourceScope).variant
        val newVariant = (newResource.scope() as AndroidResourceScope).variant
        val newVariantCheck = tree.check(newVariant)

        if (newVariantCheck.isChildOf(existingVariant)) {
            resourcesMap[key] = newResource
        }
    }
}