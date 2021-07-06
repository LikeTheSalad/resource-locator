package com.likethesalad.tools.resource.locator.android.strings.collection

import com.likethesalad.tools.resource.api.ResourceScope
import com.likethesalad.tools.resource.locator.android.collection.FileResourceCollection
import com.likethesalad.tools.resource.locator.android.strings.AndroidStringResource
import java.io.File

class FileStringResourceCollection(
    resources: List<AndroidStringResource>,
    source: File,
    scope: ResourceScope
) : FileResourceCollection<AndroidStringResource>(resources, source, scope)