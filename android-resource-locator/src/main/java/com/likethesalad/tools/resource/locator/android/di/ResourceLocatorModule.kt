package com.likethesalad.tools.resource.locator.android.di

import com.likethesalad.resource.serializer.android.AndroidResourceSerializer
import com.likethesalad.tools.resource.serializer.ResourceSerializer
import dagger.Module
import dagger.Provides

@Module
class ResourceLocatorModule {

    @Provides
    fun provideResourceSerializer(): ResourceSerializer {
        return AndroidResourceSerializer()
    }
}