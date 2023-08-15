package com.likethesalad.tools.resource.locator.android.di

import com.likethesalad.tools.agpcompat.api.bridges.AndroidExtension
import com.likethesalad.tools.resource.locator.android.AndroidResourceLocatorPlugin
import com.likethesalad.tools.resource.locator.android.providers.InstancesProvider
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

    @Provides
    @LocatorScope
    fun provideInstancesProvider(): InstancesProvider {
        return plugin
    }

    @Provides
    fun provideAndroidExtension(): AndroidExtension {
        return plugin.androidExtension
    }
}
