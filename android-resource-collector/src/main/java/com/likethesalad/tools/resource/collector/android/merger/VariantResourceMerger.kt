package com.likethesalad.tools.resource.collector.android.merger

import com.likethesalad.tools.resource.api.android.AndroidResourceScope
import com.likethesalad.tools.resource.api.android.environment.Language
import com.likethesalad.tools.resource.api.android.environment.Variant
import com.likethesalad.tools.resource.api.collection.BasicResourceCollection
import com.likethesalad.tools.resource.api.collection.ResourceCollection
import com.likethesalad.tools.resource.collector.merger.ResourceMerger

class VariantResourceMerger : ResourceMerger {

    override fun merge(collections: List<ResourceCollection>): ResourceCollection {
        val resources = collections.map { it.getAllResources() }.flatten()
        return BasicResourceCollection(resources, Any(), AndroidResourceScope(Variant.Default, Language.Default))
    }
}