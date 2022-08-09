package com.likethesalad.tools.resource.locator.android.test

import com.google.common.truth.Truth
import com.likethesalad.tools.functional.testing.AndroidProjectTest
import com.likethesalad.tools.functional.testing.app.content.ValuesResFoldersPlacer
import com.likethesalad.tools.functional.testing.app.layout.AndroidAppProjectDescriptor
import com.likethesalad.tools.functional.testing.app.layout.GradleBlockItem
import com.likethesalad.tools.functional.testing.app.layout.items.DefaultConfigAndroidBlockItem
import com.likethesalad.tools.functional.testing.app.layout.items.FlavorAndroidBlockItem
import com.likethesalad.tools.functional.testing.layout.AndroidLibProjectDescriptor
import com.likethesalad.tools.functional.testing.layout.ProjectDescriptor
import com.likethesalad.tools.functional.testing.utils.TestAssetsProvider
import com.likethesalad.tools.resource.locator.android.test.data.PluginGradleBlockItem
import org.gradle.testkit.runner.BuildResult
import org.junit.Test
import java.io.File

class TestAndroidResourceLocatorPluginTest : AndroidProjectTest() {

    private val testsDirName = "test"
    private val locatorPrefix = "prefix"
    private val inputAssetsProvider = TestAssetsProvider(testsDirName, "inputs")
    private val outputAssetsProvider = TestAssetsProvider(testsDirName, "outputs")

    @Test
    fun `Check gathering strings from single variant and single file`() {
        runInputOutputComparisonTest("basic", listOf("debug"))
    }

    @Test
    fun `Check gathering namespaced strings`() {
        runInputOutputComparisonTest("namespaced_strings", listOf("debug"))
    }

    @Test
    fun `Check gathering strings from single variant and single file twice`() {
        val variantNames = listOf("debug")
        val inOutDirName = "basic-repeated"
        val descriptor = createAppProjectDescriptor(inOutDirName)
        val inputDir = getInputTestAsset(inOutDirName)
        descriptor.projectDirectoryBuilder.register(ValuesResFoldersPlacer(inputDir))
        val commandList = variantNamesToTaskCommands(variantNames)

        createProject(descriptor)
        val result1 = buildProject(commandList, inOutDirName)

        verifyVariantResults(variantNames, inOutDirName, inOutDirName)
        verifyResultContainsLine(result1, "> Task :basic-repeated:prefixTestDebugResourceLocator")

        // Second time

        val result2 = buildProject(commandList, inOutDirName)

        verifyVariantResults(variantNames, inOutDirName, inOutDirName)
        verifyResultContainsLine(result2, "> Task :basic-repeated:prefixTestDebugResourceLocator UP-TO-DATE")
    }

    @Test
    fun `Check gathering strings from single variant and single file twice after changes to inputs`() {
        val variantNames = listOf("debug")
        val inOutDirName = "basic-repeated-changed"
        val descriptor = createAppProjectDescriptor(inOutDirName)
        val inputDir = getInputTestAsset(inOutDirName)
        val resFoldersPlacer = ValuesResFoldersPlacer(inputDir)
        descriptor.projectDirectoryBuilder.register(resFoldersPlacer)
        val commandList = variantNamesToTaskCommands(variantNames)

        createProject(descriptor)
        val result1 = buildProject(commandList, inOutDirName)

        verifyVariantResults(variantNames, inOutDirName, inOutDirName)
        verifyResultContainsLine(result1, "> Task :basic-repeated-changed:prefixTestDebugResourceLocator")

        // Second time
        val dirNames2 = "basic-repeated-changed2"
        val descriptor2 = createAppProjectDescriptor(inOutDirName)
        val inputDir2 = getInputTestAsset(dirNames2)
        descriptor2.projectDirectoryBuilder.register(ValuesResFoldersPlacer(inputDir2))
        val projectDir = getProjectDir(inOutDirName)
        resFoldersPlacer.getFilesCreated().forEach { it.delete() }
        descriptor2.projectDirectoryBuilder.buildDirectory(projectDir)

        val result2 = buildProject(commandList, inOutDirName)

        verifyVariantResults(variantNames, inOutDirName, dirNames2)
        verifyResultContainsLine(result2, "> Task :basic-repeated-changed:prefixTestDebugResourceLocator")
    }

    @Test
    fun `Check gathering strings from single variant and multiple languages twice after changes to inputs`() {
        val variantNames = listOf("debug")
        val inOutDirName = "multiplelanguages-changed"
        val descriptor = createAppProjectDescriptor(inOutDirName)
        val inputDir = getInputTestAsset(inOutDirName)
        val resFoldersPlacer = ValuesResFoldersPlacer(inputDir)
        descriptor.projectDirectoryBuilder.register(resFoldersPlacer)
        val commandList = variantNamesToTaskCommands(variantNames)

        createProject(descriptor)
        val result1 = buildProject(commandList, inOutDirName)

        verifyVariantResults(variantNames, inOutDirName, inOutDirName)
        verifyResultContainsLine(result1, "> Task :multiplelanguages-changed:prefixTestDebugResourceLocator")

        // Second time
        val dirNames2 = "multiplelanguages-changed2"
        val descriptor2 = createAppProjectDescriptor(inOutDirName)
        val inputDir2 = getInputTestAsset(dirNames2)
        descriptor2.projectDirectoryBuilder.register(ValuesResFoldersPlacer(inputDir2))
        val projectDir = getProjectDir(inOutDirName)
        resFoldersPlacer.getFilesCreated().forEach { it.delete() }
        descriptor2.projectDirectoryBuilder.buildDirectory(projectDir)

        val result2 = buildProject(commandList, inOutDirName)

        verifyVariantResults(variantNames, inOutDirName, dirNames2)
        verifyResultContainsLine(result2, "> Task :multiplelanguages-changed:prefixTestDebugResourceLocator")
    }

    @Test
    fun `Check gathering strings from resources and also android generating task`() {
        val inOutDirName = "android-generated"
        val generatedStrings = mapOf("android_generated" to "This string was generated using Androids build plugin")
        val appDescriptor = createAppProjectDescriptor(
            inOutDirName,
            listOf(
                DefaultConfigAndroidBlockItem(generatedStrings)
            )
        )
        runInputOutputComparisonTest(inOutDirName, listOf("debug"), appDescriptor)
    }

    @Test
    fun `Gather strings from multiple files, single variant`() {
        runInputOutputComparisonTest("multiplefiles", listOf("debug"))
    }

    @Test
    fun `Gather strings for multiple languages`() {
        runInputOutputComparisonTest("multiplelanguages", listOf("debug"))
    }

    @Test
    fun `Gather flavored strings`() {
        val inOutDirName = "flavored-app"
        val flavors = mutableListOf<FlavorAndroidBlockItem.FlavorDescriptor>()
        val modeFlavors = listOf("demo", "full")
        val environmentFlavors = listOf("stable", "prod")
        flavors.add(FlavorAndroidBlockItem.FlavorDescriptor("mode", modeFlavors))
        flavors.add(FlavorAndroidBlockItem.FlavorDescriptor("environment", environmentFlavors))
        val flavoredDescriptor = createAppProjectDescriptor(
            inOutDirName,
            listOf(FlavorAndroidBlockItem(flavors))
        )

        runInputOutputComparisonTest(
            inOutDirName,
            listOf(
                "fullStableDebug",
                "demoStableDebug",
                "fullProdDebug",
                "demoProdDebug"
            ),
            flavoredDescriptor
        )
    }

    @Test
    fun `Verify app that takes resources from libraries`() {
        // Create library
        val libName = "mylibrary"
        val libDescriptor = AndroidLibProjectDescriptor(libName)
        libDescriptor.projectDirectoryBuilder
            .register(ValuesResFoldersPlacer(getInputTestAsset(libName)))
        createProject(libDescriptor)

        // Set up app
        val appName = "with-library"
        val appDescriptor = createAppProjectDescriptor(
            appName,
            dependencies = listOf("implementation project(':$libName')"),
        )

        runInputOutputComparisonTest(appName, listOf("debug"), appDescriptor)
    }

    @Test
    fun `Taking namespaced strings from libraries`() {
        // Create library
        val libName = "mylibrary_namespaced"
        val libDescriptor = AndroidLibProjectDescriptor(libName)
        libDescriptor.projectDirectoryBuilder
            .register(ValuesResFoldersPlacer(getInputTestAsset(libName)))
        createProject(libDescriptor)

        // Set up app
        val appName = "with-library-namespaced"
        val appDescriptor = createAppProjectDescriptor(
            appName,
            dependencies = listOf("implementation project(':$libName')"),
        )

        runInputOutputComparisonTest(appName, listOf("debug"), appDescriptor)
    }

    @Test
    fun `Verify app that takes resources from more than one library`() {
        val libName = "mylibrary-of-libraries"
        val libName2 = "mylibrary2-of-libraries"
        val libDescriptor = AndroidLibProjectDescriptor(libName)
        val libDescriptor2 = AndroidLibProjectDescriptor(libName2)
        libDescriptor.projectDirectoryBuilder
            .register(ValuesResFoldersPlacer(getInputTestAsset(libName)))
        createProject(libDescriptor)
        libDescriptor2.projectDirectoryBuilder
            .register(ValuesResFoldersPlacer(getInputTestAsset(libName2)))
        createProject(libDescriptor2)

        // Set up app
        val appName = "with-libraries"
        val appDescriptor = createAppProjectDescriptor(
            appName,
            dependencies = listOf(
                "implementation project(':$libName')",
                "implementation project(':$libName2')"
            )
        )

        runInputOutputComparisonTest(appName, listOf("debug"), appDescriptor)
    }

    private fun runInputOutputComparisonTest(
        inOutDirName: String,
        variantNames: List<String>,
        descriptor: AndroidAppProjectDescriptor = createAppProjectDescriptor(
            inOutDirName
        )
    ) {
        val inputDir = getInputTestAsset(inOutDirName)
        descriptor.projectDirectoryBuilder.register(ValuesResFoldersPlacer(inputDir))

        createProjectAndRunResourceLocatorTasks(descriptor, variantNames)

        verifyVariantResults(variantNames, inOutDirName, inOutDirName)
    }

    private fun createAppProjectDescriptor(
        inOutDirName: String,
        blockItems: List<GradleBlockItem> = emptyList(),
        dependencies: List<String> = emptyList()
    ): AndroidAppProjectDescriptor {
        val gradleItems = listOf(PluginGradleBlockItem(locatorPrefix)) + blockItems
        return AndroidAppProjectDescriptor(inOutDirName, getPluginId(), gradleItems, dependencies)
    }

    private fun variantNamesToTaskCommands(variantNames: List<String>): List<String> {
        return variantNames.map { variantNameToTaskName(it) }
    }

    private fun variantNameToTaskName(it: String) =
        "${locatorPrefix}${getLocatorId().capitalize()}${it.capitalize()}ResourceLocator"

    private fun verifyVariantResults(variantNames: List<String>, projectName: String, outputDirName: String) {
        variantNames.forEach {
            verifyExpectedOutput(projectName, outputDirName, it)
        }
    }

    private fun verifyExpectedOutput(
        projectName: String,
        outputDirName: String,
        variantName: String
    ) {
        val taskName = variantNameToTaskName(variantName)
        val projectDir = getProjectDir(projectName)
        val resultDir = File(projectDir, "build/intermediates/incremental/$taskName")
        Truth.assertThat(resultDir.exists()).isTrue()
        verifyDirsContentsAreEqual(getExpectedOutputDir(outputDirName, taskName), resultDir)
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
        Truth.assertThat(dirFileNames2).containsExactlyElementsIn(dirFileNames1)
    }

    private fun checkIfFileIsInList(file: File, list: List<File>) {
        val fileWithSameName = list.first { it.name == file.name }
        Truth.assertThat(fileWithSameName.readText()).isEqualTo(file.readText())
    }

    private fun createProjectAndRunResourceLocatorTasks(
        projectDescriptor: ProjectDescriptor,
        variantNames: List<String>
    ): BuildResult {
        val taskNames = variantNamesToTaskCommands(variantNames)
        return createProjectAndBuild(projectDescriptor, taskNames)
    }

    private fun getInputTestAsset(inputDirName: String): File {
        return inputAssetsProvider.getAssetFile(inputDirName)
    }

    private fun getOutputTestAsset(outputDirName: String): File {
        return outputAssetsProvider.getAssetFile(outputDirName)
    }

    override fun getGradleVersion(): String = "5.6.4"

    private fun getPluginId(): String {
        return "android-resources-locator-test"
    }

    private fun getLocatorId(): String {
        return "test"
    }
}