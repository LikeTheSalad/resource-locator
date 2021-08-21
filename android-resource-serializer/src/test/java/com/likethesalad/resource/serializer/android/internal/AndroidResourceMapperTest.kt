package com.likethesalad.resource.serializer.android.internal

import com.google.common.truth.Truth
import com.likethesalad.tools.resource.api.android.AndroidResource
import com.likethesalad.tools.resource.api.android.AndroidResourceScope
import com.likethesalad.tools.resource.api.android.environment.Language
import com.likethesalad.tools.resource.api.android.environment.Variant
import com.likethesalad.tools.resource.api.android.modules.integer.IntegerAndroidResource
import com.likethesalad.tools.resource.api.android.modules.string.StringAndroidResource
import org.junit.Test

class AndroidResourceMapperTest {

    @Test
    fun `Map json to AndroidResource`() {
        verifyMappingToAndroidResource(
            AndroidResourceJsonStructure(
                mapOf("name" to "someName"), "someValue",
                "main:main", "string"
            ),
            StringAndroidResource("someName", "someValue", AndroidResourceScope(Variant.Default, Language.Default))
        )

        verifyMappingToAndroidResource(
            AndroidResourceJsonStructure(
                mapOf("name" to "anotherName", "translatable" to "false"),
                "10", "main:es", "integer"
            ),
            IntegerAndroidResource(
                mapOf("name" to "anotherName", "translatable" to "false"), 10,
                AndroidResourceScope(Variant.Default, Language.Custom("es"))
            )
        )
    }

    private fun verifyMappingToAndroidResource(
        json: AndroidResourceJsonStructure,
        expectedResource: AndroidResource
    ) {
        Truth.assertThat(AndroidResourceMapper.mapToAndroidResource(json))
            .isEqualTo(expectedResource)
    }
}