package com.likethesalad.tools.resource.locator.android.di

import dagger.Module
import dagger.Provides
import javax.xml.parsers.DocumentBuilderFactory

@Module
class MainModule {

    @Provides
    fun provideDocumentBuilderFactory(): DocumentBuilderFactory {
        return DocumentBuilderFactory.newInstance()
    }
}