package com.likethesalad.tools.resource.locator.android

import com.google.common.truth.Truth
import org.junit.Test

class AndroidResourceTest {

    private val name1 = "someName"
    private val name2 = "someOtherName"
    private val scope1 = AndroidResourceScope("someVariantName", "es")
    private val scope2 = AndroidResourceScope("someOtherVariantName", "es")

    @Test
    fun `Check equality`() {
        verifyEquality(
            AndroidResource(name1, "someValue", scope1),
            AndroidResource(name1, "someValue", scope1),
            true
        )
        verifyEquality(
            AndroidResource(name1, "someValue", scope1),
            AndroidResource(name2, "someValue", scope1),
            false
        )
        verifyEquality(
            AndroidResource(name1, "someValue", scope1),
            AndroidResource(name1, "someValue", scope2),
            false
        )
        verifyEquality(
            AndroidResource(name1, "someValue", scope1),
            AndroidResource(name1, "someValue2", scope1),
            false
        )
        verifyEquality(
            AndroidResource(name1, 1, scope1),
            AndroidResource(name1, "someValue", scope1),
            false
        )
        verifyEquality(
            AndroidResource(name2, 1, scope1),
            AndroidResource(name2, 1, scope1),
            true
        )
    }

    private fun verifyEquality(
        resource1: AndroidResource<*>,
        resource2: AndroidResource<*>,
        shouldBeEqual: Boolean
    ) {
        if (shouldBeEqual) {
            Truth.assertThat(resource1).isEqualTo(resource2)
        } else {
            Truth.assertThat(resource1).isNotEqualTo(resource2)
        }
    }
}