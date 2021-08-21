package com.likethesalad.tools.resource.api.android.utils

import kotlin.reflect.KClass

abstract class SealedParser<T : Any>(private val classItem: KClass<T>) {

    fun fromId(id: String): T {
        return classItem.sealedSubclasses
            .firstOrNull { getId(it.objectInstance) == id }
            ?.objectInstance
            ?: createUnknown(id)
    }

    private fun getId(instance: T?): String? {
        return instance?.let {
            getInstanceId(it)
        }
    }

    protected abstract fun createUnknown(id: String): T
    protected abstract fun getInstanceId(item: T): String
}