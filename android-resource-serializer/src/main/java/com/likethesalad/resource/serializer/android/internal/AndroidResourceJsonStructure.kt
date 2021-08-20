package com.likethesalad.resource.serializer.android.internal

internal data class AndroidResourceJsonStructure(
    val attributes: Map<String, String>,
    val value: String,
    val scope: String,
    val type: String
)