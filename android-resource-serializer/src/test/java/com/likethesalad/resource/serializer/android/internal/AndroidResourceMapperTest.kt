package com.likethesalad.resource.serializer.android.internal

import com.google.common.truth.Truth
import com.likethesalad.tools.resource.api.android.AndroidResource
import com.likethesalad.tools.resource.api.android.attributes.plain
import com.likethesalad.tools.resource.api.android.environment.Language
import com.likethesalad.tools.resource.api.android.environment.Variant
import com.likethesalad.tools.resource.api.android.impl.AndroidResourceScope
import com.likethesalad.tools.resource.api.android.modules.integer.IntegerAndroidResource
import com.likethesalad.tools.resource.api.android.modules.string.StringAndroidResource
import org.junit.Test

class AndroidResourceMapperTest {

    @Test
    fun `Map Json to AndroidResource`() {
        verifyMappingToAndroidResource(
            AndroidResourceJsonStructure(
                mapOf("plain|name|" to "someName"), "someValue",
                "main:main", "string"
            ),
            StringAndroidResource("someName", "someValue", AndroidResourceScope(Variant.Default, Language.Default))
        )

        verifyMappingToAndroidResource(
            AndroidResourceJsonStructure(
                mapOf("plain|name|" to "anotherName", "plain|translatable|" to "false"),
                "10", "main:es", "integer"
            ),
            IntegerAndroidResource(
                mapOf(plain("name") to "anotherName", plain("translatable") to "false"), 10,
                AndroidResourceScope(Variant.Default, Language.Custom("es"))
            )
        )
    }

    @Test
    fun `Map AndroidResource to Json`() {
        val resource1 = StringAndroidResource(
            "someName", "someValue",
            AndroidResourceScope(Variant.Dependency, Language.Default)
        )
        val resource2 = IntegerAndroidResource(
            "otherName", 15,
            AndroidResourceScope(Variant.Custom("demo"), Language.Custom("es"))
        )

        verifyMappingToJson(
            resource1,
            AndroidResourceJsonStructure(
                mapOf("plain|name|" to "someName"), "someValue", "--dependency--:main", "string"
            )
        )
        verifyMappingToJson(
            resource2,
            AndroidResourceJsonStructure(
                mapOf("plain|name|" to "otherName"), "15", "demo:es", "integer"
            )
        )
    }

    private fun verifyMappingToJson(
        resource: AndroidResource,
        expectedJson: AndroidResourceJsonStructure
    ) {
        Truth.assertThat(AndroidResourceMapper.mapToJson(resource)).isEqualTo(expectedJson)
    }

    private fun verifyMappingToAndroidResource(
        json: AndroidResourceJsonStructure,
        expectedResource: AndroidResource
    ) {
        Truth.assertThat(AndroidResourceMapper.mapToAndroidResource(json))
            .isEqualTo(expectedResource)
    }
}