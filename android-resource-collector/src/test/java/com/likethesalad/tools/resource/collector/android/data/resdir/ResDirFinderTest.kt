package com.likethesalad.tools.resource.collector.android.data.resdir

import com.google.common.truth.Truth
import com.likethesalad.tools.android.plugin.AndroidExtension
import com.likethesalad.tools.resource.api.android.environment.Variant
import com.likethesalad.tools.testing.BaseMockable
import io.mockk.every
import io.mockk.impl.annotations.MockK
import org.junit.Before
import org.junit.Test
import java.io.File

class ResDirFinderTest : BaseMockable() {

    @MockK
    lateinit var androidExtensionHelper: AndroidExtension

    private lateinit var resDirFinder: ResDirFinder

    @Before
    fun setUp() {
        resDirFinder = ResDirFinder(androidExtensionHelper)
    }

    @Test
    fun `Get res dirs from variant`() {
        val variant = Variant.Default
        val dirs = setOf(createExistingFileMock(), createExistingFileMock(), createExistingFileMock())
        prepareExtensionHelperWithDirs(variant, dirs)

        val result = resDirFinder.findResDirs(variant)

        Truth.assertThat(result).containsExactlyElementsIn(dirsToResDir(variant, dirs))
    }

    @Test
    fun `Get only existing res dirs from variant`() {
        val variant = Variant.Default
        val existingDirs = setOf(createExistingFileMock(), createExistingFileMock())
        val nonExistingDirs = setOf(createNonExistingFileMock(), createNonExistingFileMock())
        val dirs = existingDirs + nonExistingDirs
        prepareExtensionHelperWithDirs(variant, dirs)

        val result = resDirFinder.findResDirs(variant)

        Truth.assertThat(result).containsExactlyElementsIn(dirsToResDir(variant, existingDirs))
    }

    private fun dirsToResDir(variant: Variant, dirs: Set<File>): List<ResDir> {
        return dirs.map { ResDir(variant, it) }
    }

    private fun prepareExtensionHelperWithDirs(variant: Variant, dirs: Set<File>) {
        every {
            androidExtensionHelper.getVariantSrcDirs(variant.name)
        }.returns(dirs)
    }

    private fun createExistingFileMock(): File {
        val file = mockk<File>()
        every { file.exists() }.returns(true)
        return file
    }

    private fun createNonExistingFileMock(): File {
        val file = mockk<File>()
        every { file.exists() }.returns(false)
        return file
    }
}