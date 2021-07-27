package com.likethesalad.tools.resource.api.android.collection

import com.likethesalad.tools.resource.api.Resource
import com.likethesalad.tools.resource.api.ResourceScope
import java.io.File

open class FileResourceCollection(
    resources: List<Resource>,
    source: File,
    scope: ResourceScope
) : BaseResourceCollection(resources, source, scope) {

    fun getFileSource(): File = getSource() as File
}