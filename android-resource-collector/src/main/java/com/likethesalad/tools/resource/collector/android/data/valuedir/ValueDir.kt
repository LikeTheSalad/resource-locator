package com.likethesalad.tools.resource.collector.android.data.valuedir

import com.likethesalad.tools.resource.api.android.environment.Language
import com.likethesalad.tools.resource.collector.android.data.resdir.ResDir
import java.io.File

data class ValueDir(val resDir: ResDir, val dir: File, val language: Language)