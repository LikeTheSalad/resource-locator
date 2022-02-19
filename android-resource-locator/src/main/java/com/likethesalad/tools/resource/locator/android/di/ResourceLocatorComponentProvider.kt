package com.likethesalad.tools.resource.locator.android.di

import com.likethesalad.tools.resource.locator.android.AndroidResourceLocatorPlugin

object ResourceLocatorComponentProvider {

    private var component: ResourceLocatorComponent? = null

    fun init(plugin: AndroidResourceLocatorPlugin) {
        component = DaggerResourceLocatorComponent.builder()
            .collectorComponent(plugin.getCollectorComponent())
            .resourceLocatorModule(ResourceLocatorModule(plugin))
            .build()
    }

    fun getComponent(): ResourceLocatorComponent {
        return component!!
    }
}