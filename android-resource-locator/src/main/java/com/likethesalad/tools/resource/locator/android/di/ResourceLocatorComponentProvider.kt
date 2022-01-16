package com.likethesalad.tools.resource.locator.android.di

import com.likethesalad.tools.resource.locator.android.AndroidResourceLocatorPlugin

object ResourceLocatorComponentProvider {

    private var component: ResourceLocatorComponent? = null

    fun init(plugin: AndroidResourceLocatorPlugin) {
        if (component == null) {
            return
        }
        component = DaggerResourceLocatorComponent.builder()
            .resourceLocatorModule(ResourceLocatorModule(plugin))
            .build()
    }

    fun getComponent(): ResourceLocatorComponent {
        return component!!
    }
}