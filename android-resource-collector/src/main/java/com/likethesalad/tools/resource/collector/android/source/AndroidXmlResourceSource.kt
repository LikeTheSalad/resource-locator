package com.likethesalad.tools.resource.collector.android.source

import com.likethesalad.tools.resource.api.ResourceScope
import com.likethesalad.tools.resource.collector.android.data.AndroidXmlResDocument
import com.likethesalad.tools.resource.collector.source.ResourceSource
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import java.io.File

class AndroidXmlResourceSource @AssistedInject constructor(
    @Assisted private val xmlFile: File,
    @Assisted private val scope: ResourceScope,
    private val documentFactory: AndroidXmlResDocument.Factory
) : ResourceSource {

    @AssistedFactory
    interface Factory {
        fun create(xmlFile: File, scope: ResourceScope): AndroidXmlResourceSource
    }

    val document: AndroidXmlResDocument by lazy {
        documentFactory.fromFile(xmlFile)
    }

    override fun getSource(): Any = xmlFile

    override fun getScope(): ResourceScope = scope

    fun getFileSource() = getSource() as File
}