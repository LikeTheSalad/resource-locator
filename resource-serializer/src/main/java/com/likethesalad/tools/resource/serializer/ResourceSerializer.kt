package com.likethesalad.tools.resource.serializer

import com.likethesalad.tools.resource.api.Resource

interface ResourceSerializer {
    fun serialize(resource: Resource): String
    fun deserialize(string: String): Resource
}