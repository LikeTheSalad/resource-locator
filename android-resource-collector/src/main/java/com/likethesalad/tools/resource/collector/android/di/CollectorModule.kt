package com.likethesalad.tools.resource.collector.android.di

import com.likethesalad.tools.android.plugin.data.AndroidExtension
import dagger.Module
import dagger.Provides
import javax.xml.parsers.DocumentBuilderFactory

@Module
class CollectorModule(private val androidExtension: AndroidExtension) {

    @Provides
    fun provideDocumentBuilderFactory(): DocumentBuilderFactory {
        return DocumentBuilderFactory.newInstance().apply { isNamespaceAware = true }
    }

    @Provides
    fun provideAndroidExtension(): AndroidExtension {
        return androidExtension
    }
}