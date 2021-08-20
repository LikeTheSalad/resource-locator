package com.likethesalad.tools.android.plugin

import com.android.build.gradle.AppExtension

interface AndroidExtensionProvider {

    fun getExtension(): AppExtension
}