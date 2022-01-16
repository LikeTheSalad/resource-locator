package com.likethesalad.tools.resource.locator.android.di

import com.likethesalad.tools.resource.locator.android.AndroidResourceLocatorPlugin
import com.likethesalad.tools.resource.locator.android.providers.TaskFinder
import dagger.Module
import dagger.Provides

@Module
class ResourceLocatorModule(private val plugin: AndroidResourceLocatorPlugin) {

    @Provides
    @LocatorScope
    fun provideTaskFinder(): TaskFinder {
        return plugin
    }
}
