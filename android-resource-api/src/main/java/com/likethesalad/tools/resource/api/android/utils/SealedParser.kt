package com.likethesalad.tools.resource.api.android.utils

abstract class SealedParser<T : Any> {

    private val objectItems: List<T> by lazy {
        getSealedObjects().toList()
    }

    fun fromId(id: String): T {
        return objectItems.firstOrNull { getInstanceId(it) == id }
            ?: createUnknown(id)
    }

    protected abstract fun createUnknown(id: String): T
    protected abstract fun getInstanceId(item: T): String
    internal abstract fun getSealedObjects(): Set<T>
}