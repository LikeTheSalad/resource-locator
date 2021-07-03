package com.likethesalad.tools.resourcelocator.api.storing

import com.likethesalad.tools.resourcelocator.api.Resource
import com.likethesalad.tools.resourcelocator.api.ResourceCollection

interface ResourceStorer<T : ResourceCollection<out Resource<out Any>>> {
    fun store(resources: T)
}