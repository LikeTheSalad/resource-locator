package com.likethesalad.tools.resource.serializer

import com.likethesalad.tools.resource.api.collection.ResourceCollection
import com.likethesalad.tools.resource.serializer.location.ResourceCollectionLocation

interface ResourceStorage {
    fun store(resources: ResourceCollection): ResourceCollectionLocation
    fun retrieve(location: ResourceCollectionLocation): ResourceCollection
}