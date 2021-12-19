package com.likethesalad.tools.resource.locator.android.di

import com.likethesalad.tools.resource.locator.android.extension.LanguageResourceFinder
import com.likethesalad.tools.resource.serializer.ResourceSerializer
import dagger.Component

@Component(modules = [ResourceLocatorModule::class])
interface ResourceLocatorComponent {
    fun resourceSerializer(): ResourceSerializer
    fun languageResourceFinderFactory(): LanguageResourceFinder.Factory
}