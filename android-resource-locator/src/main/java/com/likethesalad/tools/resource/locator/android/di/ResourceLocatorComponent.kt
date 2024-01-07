package com.likethesalad.tools.resource.locator.android.di

import com.likethesalad.tools.resource.locator.android.extension.configuration.source.utils.CommonSourceConfigurationCreator
import com.likethesalad.tools.resource.locator.android.providers.InstancesProvider
import dagger.Component

@Component(modules = [ResourceLocatorModule::class])
@LocatorScope
interface ResourceLocatorComponent {
    fun commonSourceConfigurationCreator(): CommonSourceConfigurationCreator
    fun instancesProvider(): InstancesProvider
}