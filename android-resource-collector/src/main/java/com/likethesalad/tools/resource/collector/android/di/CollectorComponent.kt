package com.likethesalad.tools.resource.collector.android.di

import com.likethesalad.tools.resource.collector.android.data.AndroidXmlResDocument
import com.likethesalad.tools.resource.collector.android.data.resdir.ResDirFinder
import com.likethesalad.tools.resource.collector.android.data.valuedir.ValueDirFinder
import com.likethesalad.tools.resource.collector.android.data.xml.XmlFileFinder
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [CollectorModule::class])
interface CollectorComponent {
    fun resDirFinder(): ResDirFinder
    fun valueDirFinder(): ValueDirFinder
    fun xmlFileFinder(): XmlFileFinder
    fun androidXmlResDocumentFactory(): AndroidXmlResDocument.Factory
}