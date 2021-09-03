package com.likethesalad.tools.resource.collector.android.source.providers

import com.likethesalad.tools.resource.collector.source.ResourceSource
import com.likethesalad.tools.resource.collector.source.ResourceSourceProvider

class ComposableResourceSourceProvider : ResourceSourceProvider {
    private val providers: MutableList<ResourceSourceProvider> = mutableListOf()

    override fun getSources(): List<ResourceSource> {
        return providers.flatMap { it.getSources() }
    }

    fun addProvider(provider: ResourceSourceProvider) {
        if (provider in providers) {
            throw IllegalArgumentException("Provider already added: $provider")
        }
        providers.add(provider)
    }
}