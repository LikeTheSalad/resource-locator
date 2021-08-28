package com.likethesalad.tools.resource.locator.android

import com.google.common.truth.Truth
import com.likethesalad.tools.functional.testing.AndroidProjectTest
import com.likethesalad.tools.functional.testing.app.content.ValuesResFoldersPlacer
import com.likethesalad.tools.functional.testing.app.layout.AndroidAppProjectDescriptor
import com.likethesalad.tools.functional.testing.data.JarParameters
import com.likethesalad.tools.functional.testing.layout.ProjectDescriptor
import com.likethesalad.tools.functional.testing.utils.TestAssetsProvider
import org.gradle.testkit.runner.BuildResult
import org.junit.Test
import java.io.File

abstract class ResourceLocatorTest : AndroidProjectTest() {

    private val inputAssetsProvider = TestAssetsProvider("inputs")
    private val outputAssetsProvider = TestAssetsProvider("outputs")

    @Test
    fun `Check gathering strings from single variant`() {
        runInputOutputComparisonTest("basic", listOf("debug"))
    }

    private fun runInputOutputComparisonTest(
        inOutDirName: String,
        variantNames: List<String>,
        descriptor: AndroidAppProjectDescriptor = AndroidAppProjectDescriptor(
            inOutDirName,
            getPluginId()
        )
    ) {
        val inputDir = getInputTestAsset(inOutDirName)
        descriptor.projectDirectoryBuilder.register(ValuesResFoldersPlacer(inputDir))

        createProjectAndRunStringResolver(descriptor, variantNames)

        variantNames.forEach {
            verifyExpectedOutput(inOutDirName, it)
        }
    }

    private fun verifyExpectedOutput(
        inOutDirName: String,
        variantName: String
    ) {
        val projectDir = getProjectDir(inOutDirName)
        val resultDir = File(projectDir, "build/generated/resolved/$variantName")
        Truth.assertThat(resultDir.exists()).isTrue()
        verifyDirsContentsAreEqual(getExpectedOutputDir(inOutDirName, variantName), resultDir)
    }

    private fun getExpectedOutputDir(inOutDirName: String, variantName: String): File {
        // Return specific variant's outputs dir if any, else fallback to "main".
        val expectedOutputRootDir = getOutputTestAsset(inOutDirName)
        val variantOutputDir = File(expectedOutputRootDir, variantName)
        if (variantOutputDir.exists()) {
            return variantOutputDir
        }

        return File(expectedOutputRootDir, "main")
    }

    private fun verifyDirsContentsAreEqual(dir1: File, dir2: File) {
        val dir1Files = dir1.listFiles()?.asList() ?: emptyList()
        val dir2Files = dir2.listFiles()?.asList() ?: emptyList()
        if (dir1Files.isEmpty() && dir2Files.isEmpty()) {
            return
        }
        checkRootContentFileNames(dir1Files, dir2Files)
        dir1Files.forEach { dir1File ->
            if (dir1File.isFile) {
                checkIfFileIsInList(dir1File, dir2Files)
            } else {
                verifyDirsContentsAreEqual(dir1File, dir2Files.first { it.name == dir1File.name })
            }
        }
    }

    private fun checkRootContentFileNames(dirFiles1: List<File>, dirFiles2: List<File>) {
        val dirFileNames1 = dirFiles1.map { it.name }
        val dirFileNames2 = dirFiles2.map { it.name }
        Truth.assertThat(dirFileNames1).containsExactlyElementsIn(dirFileNames2)
    }

    private fun checkIfFileIsInList(file: File, list: List<File>) {
        val fileWithSameName = list.first { it.name == file.name }
        Truth.assertThat(fileWithSameName.readText()).isEqualTo(file.readText())
    }

    private fun createProjectAndRunStringResolver(
        projectDescriptor: ProjectDescriptor,
        variantNames: List<String>
    ): BuildResult {
        val commandList = variantNames.map { "${getLocatorId()}${it.capitalize()}ResourceLocator" }
        return createProjectAndRun(projectDescriptor, commandList)
    }

    private fun getInputTestAsset(inputDirName: String): File {
        return inputAssetsProvider.getAssetFile(inputDirName)
    }

    private fun getOutputTestAsset(outputDirName: String): File {
        return outputAssetsProvider.getAssetFile(outputDirName)
    }

    override fun getAndroidBuildPluginVersion(): String = "3.3.3"

    override fun getGradleVersion(): String = "4.20.3"

    override fun getPluginJarParameters(): JarParameters {
        return JarParameters("android-resource-locator", "1.0.0")
    }

    abstract fun getPluginId(): String
    abstract fun getLocatorId(): String
}