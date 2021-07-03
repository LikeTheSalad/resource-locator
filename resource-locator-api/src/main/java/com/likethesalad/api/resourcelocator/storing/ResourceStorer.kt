package com.likethesalad.api.resourcelocator.storing

import com.likethesalad.api.resourcelocator.Resource
import com.likethesalad.api.resourcelocator.ResourceCollection

interface ResourceStorer<T : ResourceCollection<out Resource<out Any>>> {
    fun store(resources: T)
}