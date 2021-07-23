package com.likethesalad.tools.resource.collector.source

interface ResourceSourceProvider {
    fun getSources(): List<ResourceSource>
}