package com.likethesalad.tools.resource.locator.android.di

import com.likethesalad.tools.resource.collector.android.di.CollectorComponent
import com.likethesalad.tools.resource.locator.android.extension.configuration.utils.CommonSourceConfigurationCreator
import dagger.Component

@Component(modules = [ResourceLocatorModule::class], dependencies = [CollectorComponent::class])
@LocatorScope
interface ResourceLocatorComponent {
    fun commonSourceConfigurationCreator(): CommonSourceConfigurationCreator
}