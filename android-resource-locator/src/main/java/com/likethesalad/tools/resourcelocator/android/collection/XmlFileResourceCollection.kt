package com.likethesalad.tools.resourcelocator.android.collection

import com.likethesalad.tools.resourcelocator.api.Resource
import com.likethesalad.tools.resourcelocator.api.collection.ResourceCollection
import java.io.File

abstract class XmlFileResourceCollection<out T : Resource<out Any>>(private val source: File) :
    ResourceCollection<T> {

    override fun getSource(): Any = source

    fun getFileSource(): File = getSource() as File
}