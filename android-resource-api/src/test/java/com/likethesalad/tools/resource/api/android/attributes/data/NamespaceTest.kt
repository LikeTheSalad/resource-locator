package com.likethesalad.tools.resource.api.android.attributes.data

import com.google.common.truth.Truth
import org.junit.Test

class NamespaceTest {

    @Test
    fun `Namespace as string`() {
        verifyNamespaceAsString(Namespace("ns1", "http://some.namespace/value"), "ns1=http://some.namespace/value")
        verifyNamespaceAsString(Namespace("ns2", "some/strange=value"), "ns2=some/strange=value")
    }

    @Test
    fun `Parse namespace`() {
        verifyParsingNamespace("ns1=http://some.namespace/value", Namespace("ns1", "http://some.namespace/value"))
        verifyParsingNamespace("ns2=some/strange=value", Namespace("ns2", "some/strange=value"))
    }

    private fun verifyNamespaceAsString(namespace: Namespace, expected: String) {
        Truth.assertThat(namespace.asString()).isEqualTo(expected)
    }

    private fun verifyParsingNamespace(value: String, expected: Namespace) {
        Truth.assertThat(Namespace.parse(value)).isEqualTo(expected)
    }
}