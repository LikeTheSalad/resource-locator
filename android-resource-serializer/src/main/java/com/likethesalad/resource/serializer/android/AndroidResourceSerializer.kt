package com.likethesalad.resource.serializer.android

import com.google.gson.GsonBuilder
import com.likethesalad.resource.serializer.android.internal.AndroidResourceJsonCollection
import com.likethesalad.resource.serializer.android.internal.AndroidResourceJsonStructure
import com.likethesalad.resource.serializer.android.internal.AndroidResourceMapper
import com.likethesalad.tools.resource.api.Resource
import com.likethesalad.tools.resource.api.android.AndroidResource
import com.likethesalad.tools.resource.api.collection.BasicResourceCollection
import com.likethesalad.tools.resource.api.collection.ResourceCollection
import com.likethesalad.tools.resource.serializer.ResourceSerializer

class AndroidResourceSerializer : ResourceSerializer {

    private val gson by lazy {
        GsonBuilder()
            .disableHtmlEscaping()
            .create()
    }

    override fun serialize(resource: Resource): String {
        val jsonStructure = AndroidResourceMapper.mapToJson(resource as AndroidResource)
        return gson.toJson(jsonStructure, AndroidResourceJsonStructure::class.java)
    }

    override fun deserialize(string: String): Resource {
        val jsonStructure = gson.fromJson(string, AndroidResourceJsonStructure::class.java)
        return AndroidResourceMapper.mapToAndroidResource(jsonStructure)
    }

    override fun serializeCollection(collection: ResourceCollection): String {
        val jsonStructureItems =
            collection.getAllResources().map { AndroidResourceMapper.mapToJson(it as AndroidResource) }
        return gson.toJson(AndroidResourceJsonCollection(jsonStructureItems))
    }

    override fun deserializeCollection(string: String): ResourceCollection {
        val jsonStructureItems = gson.fromJson(string, AndroidResourceJsonCollection::class.java)
        return BasicResourceCollection(jsonStructureItems.items.map { AndroidResourceMapper.mapToAndroidResource(it) })
    }
}