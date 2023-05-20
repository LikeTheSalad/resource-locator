package com.likethesalad.tools.resource.locator.android.providers

import org.gradle.api.provider.Provider
import java.io.File

interface TaskFinder {
    fun findTaskOutputsByName(name: String): Provider<Iterable<File>>
}