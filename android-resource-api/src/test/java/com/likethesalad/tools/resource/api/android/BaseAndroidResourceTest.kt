package com.likethesalad.tools.resource.api.android

import com.google.common.truth.Truth
import com.likethesalad.tools.resource.api.android.environment.Language
import com.likethesalad.tools.resource.api.android.environment.Variant
import com.likethesalad.tools.resource.api.android.modules.integer.IntegerAndroidResource
import com.likethesalad.tools.resource.api.android.modules.string.StringAndroidResource
import org.junit.Test

class BaseAndroidResourceTest {

    private val name1 = "someName"
    private val name2 = "someOtherName"
    private val scope1 = AndroidResourceScope(
        Variant.Custom("someVariantName"), Language.Custom("es")
    )
    private val scope2 = AndroidResourceScope(
        Variant.Custom("someOtherVariantName"),
        Language.Custom("es")
    )

    @Test
    fun `Check equality`() {
        verifyEquality(
            StringAndroidResource(name1, "someValue", scope1),
            StringAndroidResource(name1, "someValue", scope1),
            true
        )
        verifyEquality(
            StringAndroidResource(name1, "someValue", scope1),
            StringAndroidResource(name2, "someValue", scope1),
            false
        )
        verifyEquality(
            StringAndroidResource(name1, "someValue", scope1),
            StringAndroidResource(name1, "someValue", scope2),
            false
        )
        verifyEquality(
            StringAndroidResource(name1, "someValue", scope1),
            StringAndroidResource(name1, "someValue2", scope1),
            false
        )
        verifyEquality(
            IntegerAndroidResource(name1, 1, scope1),
            StringAndroidResource(name1, "someValue", scope1),
            false
        )
        verifyEquality(
            IntegerAndroidResource(name2, 1, scope1),
            IntegerAndroidResource(name2, 1, scope1),
            true
        )
    }

    private fun verifyEquality(
        resource1: BaseAndroidResource<*>,
        resource2: BaseAndroidResource<*>,
        shouldBeEqual: Boolean
    ) {
        if (shouldBeEqual) {
            Truth.assertThat(resource1).isEqualTo(resource2)
        } else {
            Truth.assertThat(resource1).isNotEqualTo(resource2)
        }
    }
}