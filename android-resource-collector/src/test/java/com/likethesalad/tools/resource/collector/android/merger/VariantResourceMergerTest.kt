package com.likethesalad.tools.resource.collector.android.merger

import com.google.common.truth.Truth
import com.likethesalad.tools.resource.api.Resource
import com.likethesalad.tools.resource.api.android.BaseAndroidResource
import com.likethesalad.tools.resource.api.android.environment.Language
import com.likethesalad.tools.resource.api.android.environment.Variant
import com.likethesalad.tools.resource.api.android.impl.AndroidResourceScope
import com.likethesalad.tools.resource.api.android.impl.AndroidResourceType
import com.likethesalad.tools.resource.api.collection.BasicResourceCollection
import com.likethesalad.tools.resource.api.collection.ResourceCollection
import com.likethesalad.tools.resource.collector.android.data.variant.VariantTree
import com.likethesalad.tools.testing.BaseMockable
import io.mockk.every
import io.mockk.impl.annotations.MockK
import org.junit.Assert
import org.junit.Test

class VariantResourceMergerTest : BaseMockable() {

    @MockK
    lateinit var tree: VariantTree

    @Test
    fun `Merge resources`() {
        val variant1 = Variant.Default
        val variant2 = Variant.Custom("demo")
        setVariantsOrder(variant1, variant2)
        val scope1 = AndroidResourceScope(variant1, Language.Default)
        val scope2 = AndroidResourceScope(variant2, Language.Default)
        val resource1 = StringResource("name1", "value1", scope1)
        val resource2 = StringResource("name2", "value2", scope1)
        val resource3 = StringResource("name3", "value3", scope2)
        val collection1 = BasicResourceCollection(listOf(resource1, resource2))
        val collection2 = BasicResourceCollection(listOf(resource3))

        val result = mergeResources(listOf(collection1, collection2))

        Truth.assertThat(result.getAllResources()).containsExactly(resource1, resource2, resource3)
    }

    @Test
    fun `Propagate error when conflict with same name, language and variant resources`() {
        val variant = Variant.Default
        val scope1 = AndroidResourceScope(variant, Language.Default)
        val scope2 = AndroidResourceScope(variant, Language.Default)
        val resource1 = StringResource("name1", "value1", scope1)
        val resource2 = StringResource("name1", "value2", scope2)
        val resource3 = StringResource("name3", "value3", scope1)
        val collection1 = BasicResourceCollection(listOf(resource1, resource3))
        val collection2 = BasicResourceCollection(listOf(resource2))

        try {
            mergeResources(listOf(collection1, collection2))
            Assert.fail()
        } catch (e: IllegalStateException) {
            Truth.assertThat(e.message).isEqualTo(
                "Found resources with the same name 'name1' " +
                        "within the same variant 'main' for the same language 'main'. " +
                        "Resource names must be unique within a single" +
                        "variant-language environment"
            )
        }
    }

    @Test
    fun `Keep children variant resources when a name and language conflict happens`() {
        val variant1 = Variant.Default
        val variant2 = Variant.Custom("demo")
        setVariantsOrder(variant1, variant2)
        val scope1 = AndroidResourceScope(variant1, Language.Default)
        val scope2 = AndroidResourceScope(variant2, Language.Default)
        val resource1 = StringResource("name1", "value1", scope1)
        val resource2 = StringResource("name1", "value2", scope2)
        val resource3 = StringResource("name3", "value3", scope1)
        val collection1 = BasicResourceCollection(listOf(resource1, resource3))
        val collection2 = BasicResourceCollection(listOf(resource2))

        val merged = mergeResources(listOf(collection1, collection2))

        Truth.assertThat(merged.getAllResources()).containsExactly(
            resource2, resource3
        )
    }

    @Test
    fun `Reuse merger`() {
        val variant1 = Variant.Default
        val variant2 = Variant.Custom("demo")
        setVariantsOrder(variant1, variant2)
        val scope1 = AndroidResourceScope(variant1, Language.Default)
        val scope2 = AndroidResourceScope(variant2, Language.Default)
        val resource1 = StringResource("name1", "value1", scope1)
        val resource2 = StringResource("name2", "value2", scope1)
        val resource3 = StringResource("name3", "value3", scope2)
        val resource4 = StringResource("name4", "value4", scope2)
        val resource5 = StringResource("name5", "value5", scope1)
        val resource6 = StringResource("name6", "value6", scope2)
        val collections1 = listOf<ResourceCollection>(
            BasicResourceCollection(listOf(resource1, resource2)),
            BasicResourceCollection(listOf(resource3))
        )
        val collections2 = listOf<ResourceCollection>(
            BasicResourceCollection(listOf(resource4)),
            BasicResourceCollection(listOf(resource5, resource6))
        )
        val merger = createMergerInstance()

        // 1st try
        Truth.assertThat(merger.merge(collections1).getAllResources()).containsExactly(
            resource1, resource2, resource3
        )

        // 2nd try
        Truth.assertThat(merger.merge(collections2).getAllResources()).containsExactly(
            resource4, resource5, resource6
        )
    }

    private fun mergeResources(
        collections: List<ResourceCollection>
    ): ResourceCollection {
        val merger = createMergerInstance()
        return merger.merge(collections)
    }

    private fun createMergerInstance() = VariantResourceMerger(tree)

    private fun setVariantsOrder(vararg variants: Variant) {
        val variantList = variants.toList()
        variantList.forEachIndexed { index, variant ->
            setParentsForVariant(variant, variantList.subList(0, index))
        }
    }

    private fun setParentsForVariant(variant: Variant, parents: List<Variant>) {
        val comparator = mockk<VariantTree.Comparator>()
        every { tree.check(variant) }.returns(comparator)
        for (parent in parents) {
            every { comparator.isChildOf(parent) }.returns(true)
        }
    }

    class StringResource(name: String, value: String, scope: AndroidResourceScope) :
        BaseAndroidResource<String>(name, value, scope) {

        override fun type(): Resource.Type {
            return AndroidResourceType.StringType
        }
    }
}