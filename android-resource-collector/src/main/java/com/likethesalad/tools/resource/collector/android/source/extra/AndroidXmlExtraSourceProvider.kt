package com.likethesalad.tools.resource.collector.android.source.extra

interface AndroidXmlExtraSourceProvider {
    fun getXmlDescriptors(): List<AndroidXmlSourceDescriptor>
}