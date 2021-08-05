package com.likethesalad.tools.resource.collector.android.providers

import com.android.build.gradle.AppExtension

interface AndroidExtensionProvider {

    fun getExtension(): AppExtension
}