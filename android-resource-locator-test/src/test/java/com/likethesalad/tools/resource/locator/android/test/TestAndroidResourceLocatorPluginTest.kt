package com.likethesalad.tools.resource.locator.android.test

import com.google.common.truth.Truth
import com.likethesalad.tools.functional.testing.AndroidTestProject
import com.likethesalad.tools.functional.testing.android.blocks.DefaultConfigAndroidBlockItem
import com.likethesalad.tools.functional.testing.android.blocks.FlavorAndroidBlockItem
import com.likethesalad.tools.functional.testing.android.descriptor.AndroidAppProjectDescriptor
import com.likethesalad.tools.functional.testing.android.descriptor.AndroidLibProjectDescriptor
import com.likethesalad.tools.functional.testing.blocks.GradleBlockItem
import com.likethesalad.tools.functional.testing.blocks.impl.plugins.GradlePluginDeclaration
import com.likethesalad.tools.functional.testing.utils.TestAssetsProvider
import com.likethesalad.tools.resource.locator.android.test.data.PluginGradleBlockItem
import org.gradle.testkit.runner.BuildResult
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import java.io.File

class TestAndroidResourceLocatorPluginTest {

    private val locatorPrefix = "prefix"
    private val inputAssetsProvider = TestAssetsProvider("inputs")
    private val outputAssetsProvider = TestAssetsProvider("outputs")

    @get:Rule
    val temporaryFolder = TemporaryFolder()

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
        val projectName = "basic-repeated"
        val descriptor = createAppProjectDescriptor(projectName)
        val commandList = variantNamesToTaskCommands(variantNames)
        val project = AndroidTestProject(getTempFile())

        project.addSubproject(descriptor)
        val result1 = project.runGradle(projectName, commandList)

        verifyVariantResults(variantNames, projectName, projectName)
        verifyResultContainsLine(result1, "> Task :basic-repeated:prefixTestDebugResourceLocator")

        // Second time

        val result2 = project.runGradle(projectName, commandList)

        verifyVariantResults(variantNames, projectName, projectName)
        verifyResultContainsLine(result2, "> Task :basic-repeated:prefixTestDebugResourceLocator UP-TO-DATE")
    }

    @Test
    fun `Check gathering strings from single variant and single file twice after changes to inputs`() {
        val variantNames = listOf("debug")
        val projectName = "basic-repeated-changed"
        val descriptor = createAppProjectDescriptor(projectName)
        val commandList = variantNamesToTaskCommands(variantNames)
        val project = AndroidTestProject(getTempFile())

        project.addSubproject(descriptor)
        val result1 = project.runGradle(projectName, commandList)

        verifyVariantResults(variantNames, projectName, projectName)
        verifyResultContainsLine(result1, "> Task :basic-repeated-changed:prefixTestDebugResourceLocator")

        // Second time
        val inputDirName2 = "basic-repeated-changed2"
        val secondInputDir = getInputTestAsset(inputDirName2)
        val projectSrcDir = getTempFile(projectName, "src")
        copyContents(secondInputDir, projectSrcDir)

        val result2 = project.runGradle(projectName, commandList)

        verifyVariantResults(variantNames, projectName, inputDirName2)
        verifyResultContainsLine(result2, "> Task :basic-repeated-changed:prefixTestDebugResourceLocator")
    }

    @Test
    fun `Check gathering strings from single variant and multiple languages twice after changes to inputs`() {
        val variantNames = listOf("debug")
        val inOutDirName = "multiplelanguages-changed"
        val descriptor = createAppProjectDescriptor(inOutDirName)
        val commandList = variantNamesToTaskCommands(variantNames)
        val project = AndroidTestProject(getTempFile())

        project.addSubproject(descriptor)
        val result1 = project.runGradle(inOutDirName, commandList)

        verifyVariantResults(variantNames, inOutDirName, inOutDirName)
        verifyResultContainsLine(result1, "> Task :multiplelanguages-changed:prefixTestDebugResourceLocator")

        // Second time
        val dirNames2 = "multiplelanguages-changed2"
        val inputDir2 = getInputTestAsset(dirNames2)
        val projectSrcDir = getTempFile(inOutDirName, "src")
        removeMatchingFiles(getInputTestAsset(inOutDirName), projectSrcDir)
        copyContents(inputDir2, projectSrcDir)

        val result2 = project.runGradle(inOutDirName, commandList)

        verifyVariantResults(variantNames, inOutDirName, dirNames2)
        verifyResultContainsLine(result2, "> Task :multiplelanguages-changed:prefixTestDebugResourceLocator")
    }

    @Test
    fun `Check gathering strings from resources and also android generating task`() {
        val inOutDirName = "android-generated"
        val generatedStrings = mapOf("android_generated" to "This string was generated using Androids build plugin")
        val appDescriptor = createAppProjectDescriptor(
            inOutDirName,
            blockItems = listOf(
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
            blockItems = listOf(FlavorAndroidBlockItem(flavors))
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
        val libDescriptor = createLibProjectDescriptor(libName)
        val project = AndroidTestProject(getTempFile())
        project.addSubproject(libDescriptor)

        // Set up app
        val appName = "with-library"
        val appDescriptor = createAppProjectDescriptor(
            appName,
            dependencies = listOf("implementation project(':$libName')"),
        )
        project.addSubproject(appDescriptor)

        runInputOutputComparisonTest(project, appName, listOf("debug"))
    }

    @Test
    fun `Taking namespaced strings from libraries`() {
        // Create library
        val libName = "mylibrary_namespaced"
        val libDescriptor = createLibProjectDescriptor(libName)
        val project = AndroidTestProject(getTempFile())
        project.addSubproject(libDescriptor)

        // Set up app
        val appName = "with-library-namespaced"
        val appDescriptor = createAppProjectDescriptor(
            appName,
            dependencies = listOf("implementation project(':$libName')"),
        )
        project.addSubproject(appDescriptor)

        runInputOutputComparisonTest(project, appName, listOf("debug"))
    }

    @Test
    fun `Verify app that takes resources from more than one library`() {
        val libName = "mylibrary-of-libraries"
        val libName2 = "mylibrary2-of-libraries"
        val libDescriptor = createLibProjectDescriptor(libName)
        val libDescriptor2 = createLibProjectDescriptor(libName2)
        val project = AndroidTestProject(getTempFile())
        project.addSubproject(libDescriptor)
        project.addSubproject(libDescriptor2)

        // Set up app
        val appName = "with-libraries"
        val appDescriptor = createAppProjectDescriptor(
            appName,
            dependencies = listOf(
                "implementation project(':$libName')",
                "implementation project(':$libName2')"
            )
        )
        project.addSubproject(appDescriptor)

        runInputOutputComparisonTest(project, appName, listOf("debug"))
    }

    private fun runInputOutputComparisonTest(
        projectName: String,
        variantNames: List<String>,
        descriptor: AndroidAppProjectDescriptor = createAppProjectDescriptor(
            projectName
        )
    ) {
        val project = AndroidTestProject(getTempFile())
        project.addSubproject(descriptor)
        runInputOutputComparisonTest(project, projectName, variantNames)
    }

    private fun runInputOutputComparisonTest(
        project: AndroidTestProject,
        projectName: String,
        variantNames: List<String>,
    ) {
        runResourceLocatorTasks(project, projectName, variantNames)

        verifyVariantResults(variantNames, projectName, projectName)
    }

    private fun createAppProjectDescriptor(
        name: String,
        inputDir: File = getInputTestAsset(name),
        blockItems: List<GradleBlockItem> = emptyList(),
        dependencies: List<String> = emptyList()
    ): AndroidAppProjectDescriptor {
        val gradleItems: List<GradleBlockItem> = listOf(PluginGradleBlockItem(locatorPrefix)) + blockItems
        val descriptor = AndroidAppProjectDescriptor(name, inputDir, gradleItems)
        descriptor.pluginsBlock.addPlugin(GradlePluginDeclaration(getPluginId()))
        dependencies.forEach {
            descriptor.dependenciesBlock.addDependency(it)
        }
        return descriptor
    }

    private fun createLibProjectDescriptor(libName: String): AndroidLibProjectDescriptor {
        return AndroidLibProjectDescriptor(libName, getInputTestAsset(libName))
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
        val projectDir = getTempFile(projectName)
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

    private fun runResourceLocatorTasks(
        project: AndroidTestProject,
        projectName: String,
        variantNames: List<String>
    ): BuildResult {
        val taskNames = variantNamesToTaskCommands(variantNames)
        return project.runGradle(projectName, taskNames)
    }

    private fun getTempFile(vararg paths: String): File {
        return if (paths.isEmpty()) {
            temporaryFolder.root
        } else {
            File(temporaryFolder.root, paths.joinToString("/"))
        }
    }

    private fun getInputTestAsset(inputDirName: String): File {
        return inputAssetsProvider.getFile(inputDirName)
    }

    private fun getOutputTestAsset(outputDirName: String): File {
        return outputAssetsProvider.getFile(outputDirName)
    }

    private fun getPluginId(): String {
        return "android-resources-locator-test"
    }

    private fun getLocatorId(): String {
        return "test"
    }

    private fun verifyResultContainsLine(result: BuildResult, line: String) {
        val lines = result.output.lines()
        Truth.assertThat(lines).contains(line)
    }

    private fun verifyResultContainsText(result: BuildResult, text: String) {
        Truth.assertThat(result.output).contains(text)
    }

    private fun copyContents(fromDir: File, intoDir: File) {
        fromDir.copyRecursively(intoDir, true)
    }

    private fun removeMatchingFiles(referenceDir: File, targetDir: File) {
        referenceDir.walkTopDown().forEach {
            if (it.isFile) {
                File(targetDir, it.toRelativeString(referenceDir)).delete()
            }
        }
    }
}