package com.likethesalad.tools.resource.locator.android.di

import com.likethesalad.tools.resource.locator.android.AndroidResourceLocatorPlugin

object ResourceLocatorComponentProvider {

    fun getComponent(plugin: AndroidResourceLocatorPlugin): ResourceLocatorComponent {
        return DaggerResourceLocatorComponent.builder()
            .collectorComponent(plugin.getCollectorComponent())
            .resourceLocatorModule(ResourceLocatorModule(plugin))
            .build()
    }
}