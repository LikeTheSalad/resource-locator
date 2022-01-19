package com.likethesalad.tools.resource.locator.android.extension.configuration.data

import com.likethesalad.tools.resource.collector.android.data.variant.VariantTree

data class ResourceLocatorInfo(
    val variantTree: VariantTree,
    val taskInfo: TaskInfo,
    val resourcesProvider: ResourcesProvider
)