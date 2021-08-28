package com.likethesalad.tools.resource.collector.android.di

import com.likethesalad.tools.resource.collector.android.AndroidResourceCollectorFactory
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [CollectorModule::class])
interface CollectorComponent {
    fun androidResourceCollectorFactory(): AndroidResourceCollectorFactory
}