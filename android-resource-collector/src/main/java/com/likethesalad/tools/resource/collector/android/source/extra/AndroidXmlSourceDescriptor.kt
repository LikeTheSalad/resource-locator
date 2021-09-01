package com.likethesalad.tools.resource.collector.android.source.extra

import com.likethesalad.tools.resource.api.android.AndroidResourceScope
import java.io.File

data class AndroidXmlSourceDescriptor(
    val file: File,
    val scope: AndroidResourceScope
)