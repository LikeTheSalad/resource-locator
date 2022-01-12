package com.likethesalad.tools.resource.collector.android.di

import com.likethesalad.tools.resource.collector.android.AndroidResourceCollector
import com.likethesalad.tools.resource.collector.android.data.resdir.ResDirFinder
import com.likethesalad.tools.resource.collector.android.source.providers.ResDirResourceSourceProvider
import com.likethesalad.tools.resource.collector.android.source.providers.VariantTreeResourceSourceProvider
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [CollectorModule::class])
interface CollectorComponent {
    fun androidResourceCollectorFactory(): AndroidResourceCollector.Factory
    fun variantTreeResourceSourceProviderFactory(): VariantTreeResourceSourceProvider.Factory
    fun resDirResourceSourceProviderFactory(): ResDirResourceSourceProvider.Factory
    fun resDirFinder(): ResDirFinder
}