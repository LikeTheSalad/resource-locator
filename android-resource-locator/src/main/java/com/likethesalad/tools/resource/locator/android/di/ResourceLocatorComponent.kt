package com.likethesalad.tools.resource.locator.android.di

import com.likethesalad.tools.resource.collector.android.di.CollectorComponent
import com.likethesalad.tools.resource.collector.android.source.providers.ResDirResourceSourceProvider
import com.likethesalad.tools.resource.locator.android.extension.configuration.source.utils.CommonSourceConfigurationCreator
import com.likethesalad.tools.resource.locator.android.providers.InstancesProvider
import dagger.Component

@Component(modules = [ResourceLocatorModule::class], dependencies = [CollectorComponent::class])
@LocatorScope
interface ResourceLocatorComponent {
    fun commonSourceConfigurationCreator(): CommonSourceConfigurationCreator
    fun resDirResourceSourceProviderFactory(): ResDirResourceSourceProvider.Factory
    fun instancesProvider(): InstancesProvider
}