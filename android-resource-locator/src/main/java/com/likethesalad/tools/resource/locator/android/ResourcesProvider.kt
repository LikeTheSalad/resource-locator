package com.likethesalad.tools.resource.locator.android

import com.likethesalad.tools.resource.api.collection.ResourceCollection

interface ResourcesProvider {
    fun getResources(): ResourceCollection
}