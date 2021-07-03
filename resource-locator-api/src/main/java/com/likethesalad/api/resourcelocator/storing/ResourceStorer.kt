package com.likethesalad.api.resourcelocator.storing

import com.likethesalad.api.resourcelocator.ResourceCollection

interface ResourceStorer<T : ResourceCollection<T>> {
    fun store(resources: T)
}