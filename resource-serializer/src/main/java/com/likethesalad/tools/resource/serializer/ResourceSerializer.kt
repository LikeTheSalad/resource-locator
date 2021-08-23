package com.likethesalad.tools.resource.serializer

import com.likethesalad.tools.resource.api.Resource
import com.likethesalad.tools.resource.api.collection.ResourceCollection

interface ResourceSerializer {
    fun serialize(resource: Resource): String
    fun deserialize(string: String): Resource
    fun serializeCollection(collection: ResourceCollection)
    fun deserializeCollection(string: String): ResourceCollection
}