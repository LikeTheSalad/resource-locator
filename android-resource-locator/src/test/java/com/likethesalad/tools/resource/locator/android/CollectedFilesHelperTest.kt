package com.likethesalad.tools.resource.locator.android

import com.google.common.truth.Truth
import com.likethesalad.tools.resource.api.android.environment.Language
import org.junit.Test

class CollectedFilesHelperTest {

    @Test
    fun `Check resource file name`() {
        verifyResourceFileName("resources.json", true)
        verifyResourceFileName("resources-es.json", true)
        verifyResourceFileName("resources-it.json", true)
        verifyResourceFileName("resources-es-ES.json", true)
        verifyResourceFileName("resource.json", false)
        verifyResourceFileName("resource-es.json", false)
        verifyResourceFileName("resources", false)
        verifyResourceFileName("resources.txt", false)
        verifyResourceFileName("something.else", false)
    }

    @Test
    fun `Get resource file name for language`() {
        verifyFileNameForLanguage(Language.Default, "resources.json")
        verifyFileNameForLanguage(Language.Custom("es"), "resources-es.json")
        verifyFileNameForLanguage(Language.Custom("es-ES"), "resources-es-ES.json")
        verifyFileNameForLanguage(Language.Custom("it"), "resources-it.json")
    }

    private fun verifyFileNameForLanguage(language: Language, expectedName: String) {
        Truth.assertThat(CollectedFilesHelper.getResourceFileName(language)).isEqualTo(expectedName)
    }

    private fun verifyResourceFileName(name: String, expectedResult: Boolean) {
        Truth.assertThat(CollectedFilesHelper.isResourceFileName(name)).isEqualTo(expectedResult)
    }
}