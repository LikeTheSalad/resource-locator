package com.likethesalad.resource.serializer.android

import com.google.gson.Gson
import com.likethesalad.resource.serializer.android.internal.AndroidResourceJsonStructure
import com.likethesalad.resource.serializer.android.internal.AndroidResourceMapper
import com.likethesalad.tools.resource.api.Resource
import com.likethesalad.tools.resource.api.android.AndroidResource
import com.likethesalad.tools.resource.serializer.ResourceSerializer
import java.util.Base64

class AndroidResourceSerializer : ResourceSerializer {

    private val gson by lazy { Gson() }
    private val encoder by lazy { Base64.getEncoder() }
    private val decoder by lazy { Base64.getDecoder() }

    override fun serialize(resource: Resource): String {
        val jsonStructure = AndroidResourceMapper.mapToJson(resource as AndroidResource)
        val jsonString = gson.toJson(jsonStructure, AndroidResourceJsonStructure::class.java)
        return encode(jsonString)
    }

    override fun deserialize(string: String): Resource {
        val jsonString = decode(string)
        val jsonStructure = gson.fromJson(jsonString, AndroidResourceJsonStructure::class.java)
        return AndroidResourceMapper.mapToAndroidResource(jsonStructure)
    }

    private fun encode(jsonString: String): String {
        return encoder.encodeToString(jsonString.toByteArray())
    }

    private fun decode(base64String: String): String {
        return String(decoder.decode(base64String))
    }
}