package com.likethesalad.android.string.resources.locator.extractor

import com.google.common.truth.Truth
import com.likethesalad.tools.resource.api.android.attributes.namespaced
import com.likethesalad.tools.resource.api.android.attributes.plain
import com.likethesalad.tools.resource.api.android.environment.Language
import com.likethesalad.tools.resource.api.android.environment.Variant
import com.likethesalad.tools.resource.api.android.impl.AndroidResourceScope
import com.likethesalad.tools.resource.api.android.modules.string.StringAndroidResource
import com.likethesalad.tools.resource.collector.android.data.AndroidXmlResDocument
import com.likethesalad.tools.testing.DummyResourcesFinder
import org.junit.Before
import org.junit.Test
import javax.xml.parsers.DocumentBuilderFactory

class StringXmlResourceExtractorTest {

    private lateinit var androidXmlResDocumentFactory: AndroidXmlResDocument.Factory

    @Before
    fun setUp() {
        androidXmlResDocumentFactory = AndroidXmlResDocument.Factory(DocumentBuilderFactory.newInstance())
    }

    @Test
    fun `Get string resources from Android XML file`() {
        val file = DummyResourcesFinder.getResourceFile("androidStrings/strings.xml")
        val scope = AndroidResourceScope(Variant.Default, Language.Default)
        val instance = StringXmlResourceExtractor()

        val result = instance.getResourcesFromAndroidDocument(androidXmlResDocumentFactory.fromFile(file), scope)

        Truth.assertThat(result).containsExactly(
            StringAndroidResource("string_1", "Some string", scope),
            StringAndroidResource("string_6", "\\\"Some escaped quoted string\\\"", scope),
            StringAndroidResource("string_4", "Some partially quoted string", scope),
            StringAndroidResource("string_5", "Some partially quoted escaped \\\"string\\\"", scope),
            StringAndroidResource("string_7", "Just one more string", scope),
            StringAndroidResource("string_3", "Some quoted string", scope),
            StringAndroidResource(
                mapOf(plain("name") to "string_2", plain("translatable") to "false"),
                "Some not translatable string",
                scope
            )
        )
    }

    @Test
    fun `Get string resources from Android XML file with namespaces`() {
        val file = DummyResourcesFinder.getResourceFile("androidStrings/strings_with_namespaces.xml")
        val scope = AndroidResourceScope(Variant.Default, Language.Default)
        val instance = StringXmlResourceExtractor()

        val result = instance.getResourcesFromAndroidDocument(androidXmlResDocumentFactory.fromFile(file), scope)

        Truth.assertThat(result).containsExactly(
            StringAndroidResource("string_1", "Some string", scope),
            StringAndroidResource(
                mapOf(
                    plain("name") to "string_2",
                    namespaced("something", "http://schemas.android.com/tools") to "somethingValue1"
                ), "Some string with namespace", scope
            ),
            StringAndroidResource(
                mapOf(
                    plain("name") to "string_3",
                    namespaced("nsname", "http://schemas.android.com/apk/res-auto") to "nsnameValue1"
                ), "Some string with another namespace", scope
            ),
            StringAndroidResource(
                mapOf(
                    plain("name") to "string_4",
                    namespaced("sth", "http://schemas.android.com/tools") to "sthValue",
                    namespaced("nsname2", "http://schemas.android.com/apk/res-auto") to "nsname2Value"
                ), "Some string with many namespaces", scope
            ),
            StringAndroidResource("string_5", "Just one more string", scope),
        )
    }
}