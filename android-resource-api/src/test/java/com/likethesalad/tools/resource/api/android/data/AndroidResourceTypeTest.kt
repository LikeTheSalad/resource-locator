package com.likethesalad.tools.resource.api.android.data

import com.google.common.truth.Truth
import org.junit.Test

class AndroidResourceTypeTest {

    @Test
    fun `Parse item id to object`() {
        val id = "integer"

        val result = AndroidResourceType.fromId(id)

        Truth.assertThat(result).isEqualTo(AndroidResourceType.IntegerType)
    }

    @Test
    fun `Check that all objects are passed to the parser`() {
        val objects = AndroidResourceType::class.sealedSubclasses.mapNotNull { it.objectInstance }

        val objectsInParser = AndroidResourceType.getSealedObjects()

        Truth.assertThat(objectsInParser).containsExactlyElementsIn(objects)
    }
}