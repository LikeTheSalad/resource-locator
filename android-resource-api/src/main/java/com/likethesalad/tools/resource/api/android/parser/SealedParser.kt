package com.likethesalad.tools.resource.api.android.parser

abstract class SealedParser<T : SealedParseable> {

    private val objectItems: List<T> by lazy {
        getSealedObjects().toList()
    }

    fun fromId(id: String): T {
        return objectItems.firstOrNull { it.getSealedItemId() == id }
            ?: createUnknown(id)
    }

    protected abstract fun createUnknown(id: String): T
    internal abstract fun getSealedObjects(): Set<T>
}