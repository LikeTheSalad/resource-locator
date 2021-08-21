package com.likethesalad.tools.resource.api.android.utils

import kotlin.reflect.KClass

abstract class SealedParser<T : Any>(private val classItem: KClass<T>) {

    private val objectItems: List<T> by lazy {
        classItem.sealedSubclasses.mapNotNull { it.objectInstance }
    }

    fun fromId(id: String): T {
        return objectItems.firstOrNull { getInstanceId(it) == id }
            ?: createUnknown(id)
    }

    protected abstract fun createUnknown(id: String): T
    protected abstract fun getInstanceId(item: T): String
}