package com.likethesalad.tools.resource.collector.android.di

import com.likethesalad.tools.resource.collector.android.AndroidResourceCollectorFactory
import com.likethesalad.tools.resource.collector.android.source.providers.ResDirResourceSourceProviderFactory
import com.likethesalad.tools.resource.collector.android.source.providers.VariantTreeResourceSourceProviderFactory
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [CollectorModule::class])
interface CollectorComponent {
    fun androidResourceCollectorFactory(): AndroidResourceCollectorFactory
    fun variantTreeResourceSourceProviderFactory(): VariantTreeResourceSourceProviderFactory
    fun resDirResourceSourceProviderFactory(): ResDirResourceSourceProviderFactory
}