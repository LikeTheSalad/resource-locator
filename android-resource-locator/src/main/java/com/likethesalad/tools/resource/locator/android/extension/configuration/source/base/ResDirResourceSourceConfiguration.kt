package com.likethesalad.tools.resource.locator.android.extension.configuration.source.base

import com.likethesalad.tools.resource.collector.android.data.resdir.ResDir
import com.likethesalad.tools.resource.collector.android.data.variant.VariantTree
import com.likethesalad.tools.resource.collector.android.source.providers.ResDirResourceSourceProvider
import com.likethesalad.tools.resource.collector.source.ResourceSourceProvider
import com.likethesalad.tools.resource.locator.android.extension.configuration.source.ResourceSourceConfiguration

abstract class ResDirResourceSourceConfiguration(variantTree: VariantTree) : ResourceSourceConfiguration(variantTree) {

    override fun doGetSourceProviders(): List<ResourceSourceProvider> {
        return getResDirs().map {
            ResDirResourceSourceProvider(it)
        }
    }

    abstract fun getResDirs(): List<ResDir>
}