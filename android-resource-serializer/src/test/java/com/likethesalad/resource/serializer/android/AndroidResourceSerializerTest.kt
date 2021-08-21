package com.likethesalad.resource.serializer.android

import com.google.common.truth.Truth
import com.likethesalad.tools.resource.api.Resource
import com.likethesalad.tools.resource.api.android.AndroidResource
import com.likethesalad.tools.resource.api.android.AndroidResourceScope
import com.likethesalad.tools.resource.api.android.environment.Language
import com.likethesalad.tools.resource.api.android.environment.Variant
import com.likethesalad.tools.resource.api.android.modules.string.StringAndroidResource
import org.junit.Test

class AndroidResourceSerializerTest {

    private val serializer = AndroidResourceSerializer()

    @Test
    fun `Serialize AndroidResource`() {
        val resource = StringAndroidResource(
            "someName", "someValue",
            AndroidResourceScope(Variant.Default, Language.Custom("es"))
        )

        val result = serialize(resource)

        Truth.assertThat(result).isEqualTo(
            "eyJhdHRyaWJ1dGVzIjp7Im5hbWUiOiJzb21lTmFtZSJ9LCJ2YWx1ZSI6InNvbWVWYWx1ZSIsInNjb3BlIjoibWFpbjplcyIsInR5cGUiOiJzdHJpbmcifQ=="
        )
    }

    @Test
    fun `Deserialize AndroidResource`() {
        val serialized =
            "eyJhdHRyaWJ1dGVzIjp7Im5hbWUiOiJzb21lTmFtZSJ9LCJ2YWx1ZSI6InNvbWVWYWx1ZSIsInNjb3BlIjoibWFpbjplcyIsInR5cGUiOiJzdHJpbmcifQ=="

        val result = deserialize(serialized)

        Truth.assertThat(result).isEqualTo(
            StringAndroidResource(
                "someName", "someValue",
                AndroidResourceScope(Variant.Default, Language.Custom("es"))
            )
        )
    }

    private fun deserialize(serialized: String): Resource {
        return serializer.deserialize(serialized)
    }

    private fun serialize(resource: AndroidResource): String {
        return serializer.serialize(resource)
    }
}