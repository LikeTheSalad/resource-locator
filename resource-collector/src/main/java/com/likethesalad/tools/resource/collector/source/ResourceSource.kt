package com.likethesalad.tools.resource.collector.source

import com.likethesalad.tools.resource.api.Resource

interface ResourceSource {
    fun getSource(): Any
    fun getScope(): Resource.Scope
}