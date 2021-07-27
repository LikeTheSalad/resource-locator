package com.likethesalad.tools.resource.locator.android.collection

import com.likethesalad.tools.resource.api.Resource
import com.likethesalad.tools.resource.api.ResourceScope
import com.likethesalad.tools.resource.api.collection.BasicResourceCollection
import java.io.File

open class FileResourceCollection(
    resources: List<Resource>,
    source: File,
    scope: ResourceScope
) : BasicResourceCollection(resources, source, scope) {

    fun getFileSource(): File = getSource() as File
}