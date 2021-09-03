package com.likethesalad.tools.resource.collector.android.di

object CollectorComponentProvider {
    private var component: CollectorComponent? = null

    fun getComponent(): CollectorComponent {
        if (component == null) {
            component = DaggerCollectorComponent.builder()
                .collectorModule(CollectorModule())
                .build()
        }

        return component!!
    }
}