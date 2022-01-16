package com.likethesalad.tools.resource.locator.android.di

import com.likethesalad.tools.resource.locator.android.AndroidResourceLocatorPlugin

object ResourceLocatorComponentProvider {

    private var component: ResourceLocatorComponent? = null

    fun getComponent(plugin: AndroidResourceLocatorPlugin): ResourceLocatorComponent {
        if (component == null) {
            component = DaggerResourceLocatorComponent.builder()
                .resourceLocatorModule(ResourceLocatorModule(plugin))
                .build()
        }
        return component!!
    }
}