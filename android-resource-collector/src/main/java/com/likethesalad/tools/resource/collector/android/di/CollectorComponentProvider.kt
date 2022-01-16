package com.likethesalad.tools.resource.collector.android.di

import com.likethesalad.tools.android.plugin.data.AndroidExtension

object CollectorComponentProvider {

    private var component: CollectorComponent? = null

    fun getComponent(): CollectorComponent {
        return component!!
    }

    fun initialize(androidExtension: AndroidExtension) {
        if (component != null) {
            return
        }
        component = DaggerCollectorComponent.builder()
            .collectorModule(CollectorModule(androidExtension))
            .build()
    }
}