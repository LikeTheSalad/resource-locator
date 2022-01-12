package com.likethesalad.tools.resource.locator.android.extension.listener

import com.likethesalad.tools.resource.collector.android.data.variant.VariantTree
import com.likethesalad.tools.resource.locator.android.extension.listener.data.ResourceLocatorInfo

interface ResourceLocatorListener {
    fun onLocatorReady(variantTree: VariantTree, info: ResourceLocatorInfo)
}