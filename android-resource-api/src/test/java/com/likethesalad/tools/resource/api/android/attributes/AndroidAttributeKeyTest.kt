package com.likethesalad.tools.resource.api.android.attributes

import com.google.common.truth.Truth
import org.junit.Test

class AndroidAttributeKeyTest {

    @Test
    fun `Verify plain attributeKey name`() {
        val attributeKey = AndroidAttributeKey.Plain("someKey")

        Truth.assertThat(attributeKey.getName()).isEqualTo("plain|someKey|")
    }

    @Test
    fun `Verify namespaced attributeKey name`() {
        val attributeKey = AndroidAttributeKey.Namespaced("someKey", "http://some.namespace.value")

        Truth.assertThat(attributeKey.getName()).isEqualTo("namespaced|someKey|http://some.namespace.value")
    }

    @Test
    fun `Verify parsing plain attributeKey`() {
        val attributeKey = AndroidAttributeKey.fromName("plain|theKey|")

        Truth.assertThat(attributeKey).isEqualTo(AndroidAttributeKey.Plain("theKey"))
    }

    @Test
    fun `Verify parsing namespaced attributeKey`() {
        val attributeKey = AndroidAttributeKey.fromName("namespaced|theKey|someNamespaceValue")

        Truth.assertThat(attributeKey).isEqualTo(AndroidAttributeKey.Namespaced("theKey", "someNamespaceValue"))
    }
}