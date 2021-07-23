package com.likethesalad.tools.resource.locator.android.modules.language

import com.likethesalad.tools.resource.api.collection.ResourceCollection
import com.likethesalad.tools.resource.collector.ResourceExtractor
import com.likethesalad.tools.resource.locator.android.data.android.AndroidVariant
import com.likethesalad.tools.resource.locator.android.tools.android.ResDirFinder

class VariantResourceExtractor(
    private val variant: AndroidVariant,
    private val resDirFinder: ResDirFinder
) : ResourceExtractor {

    override fun extract(): ResourceCollection {
        val resDirs = resDirFinder.findResDirs(variant)

    }
}