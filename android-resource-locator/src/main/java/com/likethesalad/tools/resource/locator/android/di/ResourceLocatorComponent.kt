package com.likethesalad.tools.resource.locator.android.di

import com.likethesalad.tools.resource.locator.android.extension.configuration.utils.CommonSourceConfigurationCreator
import dagger.Component
import javax.inject.Singleton

@Component(modules = [ResourceLocatorModule::class])
@Singleton
interface ResourceLocatorComponent {
    fun commonSourceConfigurationCreator(): CommonSourceConfigurationCreator
}