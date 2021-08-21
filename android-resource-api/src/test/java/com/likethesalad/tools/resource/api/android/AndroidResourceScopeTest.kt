package com.likethesalad.tools.resource.api.android

import com.google.common.truth.Truth
import com.likethesalad.tools.resource.api.android.environment.Language
import com.likethesalad.tools.resource.api.android.environment.Variant
import org.junit.Assert.fail
import org.junit.Test

class AndroidResourceScopeTest {

    @Test
    fun `Get scope name`() {
        checkScopeName(
            AndroidResourceScope(Variant.Default, Language.Default),
            "main:main"
        )
        checkScopeName(
            AndroidResourceScope(Variant.Custom("demo"), Language.Default),
            "demo:main"
        )
        checkScopeName(
            AndroidResourceScope(Variant.Default, Language.Custom("es")),
            "main:es"
        )
        checkScopeName(
            AndroidResourceScope(Variant.Custom("paid"), Language.Custom("en")),
            "paid:en"
        )
    }

    @Test
    fun `Retrieve scope from name`() {
        val originalScope = AndroidResourceScope(Variant.Default, Language.Custom("es"))

        val scope = AndroidResourceScope.fromName(originalScope.getName())

        Truth.assertThat(scope).isEqualTo(originalScope)
    }

    @Test
    fun `Fail if string is not valid when parsing scope from string`() {
        try {
            AndroidResourceScope.fromName("something")
            fail()
        } catch (e: IllegalArgumentException) {
            Truth.assertThat(e.message).isEqualTo(
                "Invalid AndroidResourceScope format" +
                        " for: 'something'"
            )
        }
    }

    private fun checkScopeName(scope: AndroidResourceScope, expectedName: String) {
        Truth.assertThat(scope.getName()).isEqualTo(expectedName)
    }
}