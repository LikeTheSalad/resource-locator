package com.likethesalad.resource.serializer.android.internal

import org.junit.Test

class AndroidResourceMapperTest {

    @Test
    fun `Map json to AndroidResource`() {
        val attrs = mapOf(
            "name" to "someName",
            "translatable" to "false"
        )
        val value = "someValue"
        val scopeName = ""
        val json = AndroidResourceJsonStructure(attrs, value, )
    }
}