package com.likethesalad.tools.resource.locator.android.collection

import com.likethesalad.tools.resource.api.Resource
import com.likethesalad.tools.resource.api.ResourceScope
import java.io.File

open class FileResourceCollection<out T : Resource<out Any>>(source: File, scope: ResourceScope) :
    BaseResourceCollection<T>(source, scope) {

    fun getFileSource(): File = getSource() as File
}