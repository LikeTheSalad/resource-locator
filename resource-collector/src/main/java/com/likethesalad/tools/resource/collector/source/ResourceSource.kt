package com.likethesalad.tools.resource.collector.source

import com.likethesalad.tools.resource.api.ResourceScope

interface ResourceSource {
    fun getSource(): Any
    fun getScope(): ResourceScope
}