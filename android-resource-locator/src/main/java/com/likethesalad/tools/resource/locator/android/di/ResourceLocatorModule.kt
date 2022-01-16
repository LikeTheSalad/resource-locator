package com.likethesalad.tools.resource.locator.android.di

import com.likethesalad.tools.resource.locator.android.AndroidResourceLocatorPlugin
import com.likethesalad.tools.resource.locator.android.providers.TaskFinder
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ResourceLocatorModule(private val plugin: AndroidResourceLocatorPlugin) {

    @Provides
    @Singleton
    fun provideTaskFinder(): TaskFinder {
        return plugin
    }
}
