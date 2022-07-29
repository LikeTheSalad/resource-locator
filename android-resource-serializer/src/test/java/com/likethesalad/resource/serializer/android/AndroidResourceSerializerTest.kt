package com.likethesalad.resource.serializer.android

import com.google.common.truth.Truth
import com.likethesalad.tools.resource.api.Resource
import com.likethesalad.tools.resource.api.android.AndroidResource
import com.likethesalad.tools.resource.api.android.attributes.plain
import com.likethesalad.tools.resource.api.android.environment.Language
import com.likethesalad.tools.resource.api.android.environment.Variant
import com.likethesalad.tools.resource.api.android.impl.AndroidResourceScope
import com.likethesalad.tools.resource.api.android.modules.integer.IntegerAndroidResource
import com.likethesalad.tools.resource.api.android.modules.string.StringAndroidResource
import com.likethesalad.tools.resource.api.collection.BasicResourceCollection
import com.likethesalad.tools.resource.api.collection.ResourceCollection
import org.junit.Test

class AndroidResourceSerializerTest {

    private val serializer = AndroidResourceSerializer()

    @Test
    fun `Serialize AndroidResource`() {
        val resource = StringAndroidResource(
            "someName", "someValue",
            AndroidResourceScope(Variant.Default, Language.Custom("es"))
        )

        val stringResource = serialize(resource)

        Truth.assertThat(deserialize(stringResource)).isEqualTo(
            resource
        )
    }

    @Test
    fun `Test serialize AndroidResource collection`() {
        val resource1 = StringAndroidResource(
            mapOf(
                plain("name") to "someName",
                plain("translatable") to "false"
            ), "someValue", AndroidResourceScope(Variant.Default, Language.Default)
        )
        val resource2 = IntegerAndroidResource(
            "someIntName",
            20,
            AndroidResourceScope(Variant.Custom("demo"), Language.Custom("es"))
        )
        val collection = BasicResourceCollection(listOf(resource1, resource2))

        val stringCollection = serializeCollection(collection)
        println("Resource collection: $stringCollection")

        Truth.assertThat(deserializeCollection(stringCollection)).isEqualTo(
            collection
        )
    }

    private fun serializeCollection(collection: ResourceCollection): String {
        return serializer.serializeCollection(collection)
    }

    private fun deserializeCollection(string: String): ResourceCollection {
        return serializer.deserializeCollection(string)
    }

    private fun deserialize(serialized: String): Resource {
        return serializer.deserialize(serialized)
    }

    private fun serialize(resource: AndroidResource): String {
        return serializer.serialize(resource)
    }
}