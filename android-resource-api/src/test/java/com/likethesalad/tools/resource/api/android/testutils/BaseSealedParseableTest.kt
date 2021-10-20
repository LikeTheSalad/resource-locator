package com.likethesalad.tools.resource.api.android.testutils

import com.google.common.truth.Truth
import com.likethesalad.tools.resource.api.android.parser.SealedParseable
import com.likethesalad.tools.resource.api.android.parser.SealedParser
import org.junit.Test
import kotlin.reflect.KClass
import kotlin.reflect.full.companionObjectInstance

abstract class BaseSealedParseableTest<T : SealedParseable>(private val klass: KClass<T>) {

    @Suppress("UNCHECKED_CAST")
    private val sealedCompanionParser by lazy {
        klass.companionObjectInstance as SealedParser<T>
    }

    @Test
    fun `Check that all objects are passed to the parser`() {
        val objects = getSealedObjects()

        val objectsInParser = sealedCompanionParser.getSealedObjects()

        Truth.assertThat(objectsInParser).containsExactlyElementsIn(objects)
    }

    @Test
    fun `Check that all objects are parcelable through their IDs`() {
        val objects = getSealedObjects()

        for (sealedObject in objects) {
            val parsed = sealedCompanionParser.fromId(sealedObject.getSealedItemId())
            Truth.assertThat(parsed).isEqualTo(sealedObject)
        }
    }

    @Test
    fun `Check that unknown type is created if no existing ID can be found when parsing`() {
        val unknownId = "theresnowaythiscanbeanid"

        val result = sealedCompanionParser.fromId(unknownId)

        Truth.assertThat(result.javaClass).isEqualTo(getUnknownType())
        Truth.assertThat(result.getSealedItemId()).isEqualTo(unknownId)
    }

    private fun getSealedObjects() = klass.sealedSubclasses.mapNotNull { it.objectInstance }

    abstract fun getUnknownType(): Class<out T>
}