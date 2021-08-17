package com.likethesalad.tools.resource.collector.android.merger

import com.google.common.truth.Truth
import com.likethesalad.tools.resource.api.android.AndroidResourceScope
import com.likethesalad.tools.resource.api.android.BaseAndroidResource
import com.likethesalad.tools.resource.api.android.data.AndroidResourceType
import com.likethesalad.tools.resource.api.android.environment.Language
import com.likethesalad.tools.resource.api.android.environment.Variant
import com.likethesalad.tools.resource.api.collection.BasicResourceCollection
import com.likethesalad.tools.resource.api.collection.ResourceCollection
import com.likethesalad.tools.resource.api.data.ResourceType
import org.junit.Test

class VariantResourceMergerTest {

    private val merger = VariantResourceMerger()

    @Test
    fun `Merge resources`() {
        val scope1 = AndroidResourceScope(Variant.Default, Language.Default)
        val scope2 = AndroidResourceScope(Variant.Custom("demo"), Language.Default)
        val resource1 = StringResource("name1", "value1", scope1)
        val resource2 = StringResource("name2", "value2", scope1)
        val resource3 = StringResource("name3", "value3", scope2)
        val collection1 = BasicResourceCollection(listOf(resource1, resource2))
        val collection2 = BasicResourceCollection(listOf(resource3))

        val result = mergeResources(listOf(collection1, collection2))

        Truth.assertThat(result.getAllResources()).containsExactly(resource1, resource2, resource3)
    }

    @Test
    fun `Keep children variant resources when conflict`() {
        // val collection1 = BasicResourceCollection()
    }

    private fun mergeResources(
        collections: List<ResourceCollection>
    ): ResourceCollection {
        return merger.merge(collections)
    }

    class StringResource(name: String, value: String, scope: AndroidResourceScope) :
        BaseAndroidResource<String>(name, value, scope) {

        override fun type(): ResourceType {
            return AndroidResourceType.StringType
        }
    }

    class IntegerResource(name: String, value: Int, scope: AndroidResourceScope) :
        BaseAndroidResource<Int>(name, value, scope) {

        override fun type(): ResourceType {
            return AndroidResourceType.IntegerType
        }
    }
}