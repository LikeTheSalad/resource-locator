package com.likethesalad.tools.resource.collector.android.data.valuedir

import com.google.common.truth.Truth
import com.likethesalad.tools.resource.api.android.environment.Language
import com.likethesalad.tools.resource.api.android.environment.Variant
import com.likethesalad.tools.resource.collector.android.data.resdir.ResDir
import com.likethesalad.tools.testing.DummyResourcesFinder.getResourceFile
import org.junit.Test

class ValueDirFinderTest {

    @Test
    fun `Find values dirs`() {
        val resFolder = getResourceFile("res")
        val resDir = ResDir(Variant.Default, resFolder)

        val result = ValueDirFinder.findValueDirs(resDir)

        Truth.assertThat(result).containsExactly(
            ValueDir(resDir, getResourceFile("res/values"), Language.Default),
            ValueDir(resDir, getResourceFile("res/values-es"), Language.Custom("es")),
            ValueDir(resDir, getResourceFile("res/values-es-rUS"), Language.Custom("es-rUS")),
            ValueDir(resDir, getResourceFile("res/values-v5"), Language.Custom("v5")),
            ValueDir(resDir, getResourceFile("res/values-v30"), Language.Custom("v30"))
        )
    }
}