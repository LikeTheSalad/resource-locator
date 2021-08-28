package com.likethesalad.tools.resource.collector.android.di

import dagger.Module
import dagger.Provides
import javax.xml.parsers.DocumentBuilderFactory

@Module
class CollectorModule {

    @Provides
    fun provideDocumentBuilderFactory(): DocumentBuilderFactory {
        return DocumentBuilderFactory.newInstance()
    }
}