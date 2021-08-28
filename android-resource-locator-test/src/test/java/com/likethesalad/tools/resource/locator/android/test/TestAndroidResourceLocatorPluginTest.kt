package com.likethesalad.tools.resource.locator.android.test

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

class TestAndroidResourceLocatorPluginTest : AndroidProjectTest() {

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

        val taskNames = variantNames.map { "${getLocatorId()}${it.capitalize()}ResourceLocator" }
        createProjectAndRunResourceLocatorTasks(descriptor, taskNames)

        taskNames.forEach {
            verifyExpectedOutput(inOutDirName, it)
        }
    }

    private fun verifyExpectedOutput(
        inOutDirName: String,
        taskName: String
    ) {
        val projectDir = getProjectDir(inOutDirName)
        val resultDir = File(projectDir, "build/intermediates/incremental/$taskName")
        Truth.assertThat(resultDir.exists()).isTrue()
        verifyDirsContentsAreEqual(getExpectedOutputDir(inOutDirName, taskName), resultDir)
    }

    private fun getExpectedOutputDir(inOutDirName: String, taskName: String): File {
        val expectedOutputRootDir = getOutputTestAsset(inOutDirName)
        val variantOutputDir = File(expectedOutputRootDir, taskName)

        if (!variantOutputDir.exists()) {
            throw IllegalStateException("Dir not found: $variantOutputDir")
        }

        return variantOutputDir
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

    private fun createProjectAndRunResourceLocatorTasks(
        projectDescriptor: ProjectDescriptor,
        taskNames: List<String>
    ): BuildResult {
        return createProjectAndRun(projectDescriptor, taskNames)
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
        return JarParameters("android-resource-locator-test")
    }

    private fun getPluginId(): String {
        return "android-resources-locator-test"
    }

    private fun getLocatorId(): String {
        return "test"
    }
}