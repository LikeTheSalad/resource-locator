package com.likethesalad.tools.resource.locator.android.providers

import org.gradle.api.provider.Provider
import java.util.concurrent.Callable

interface InstancesProvider {
    fun <T> createProvider(callable: Callable<T>): Provider<T>
}