package com.likethesalad.tools.resource.collector.android.source.extra

interface AndroidXmlExtraResourceProvider {
    fun getXmlDescriptors(): List<AndroidXmlResourceDescriptor>
}