package com.likethesalad.tools.resource.extractor

interface ResourceSourceProvider {
    fun getSources(): List<ResourceSource>
}