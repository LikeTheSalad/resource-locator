package com.likethesalad.tools.resource.collector.android.source

import com.likethesalad.tools.resource.api.ResourceScope
import com.likethesalad.tools.resource.collector.android.data.AndroidXmlResDocument
import com.likethesalad.tools.resource.collector.source.base.FileResourceSource
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import java.io.File

class AndroidXmlResourceSource @AssistedInject constructor(
    @Assisted private val xmlFile: File,
    @Assisted private val scope: ResourceScope,
    private val documentFactory: AndroidXmlResDocument.Factory
) : FileResourceSource(xmlFile) {

    @AssistedFactory
    interface Factory {
        fun create(xmlFile: File, scope: ResourceScope): AndroidXmlResourceSource
    }

    val document: AndroidXmlResDocument by lazy {
        documentFactory.fromFile(xmlFile)
    }

    override fun getScope(): ResourceScope = scope
}