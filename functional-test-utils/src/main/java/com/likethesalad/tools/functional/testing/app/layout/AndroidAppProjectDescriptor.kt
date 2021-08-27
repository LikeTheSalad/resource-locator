package com.likethesalad.tools.functional.testing.app.layout

import com.likethesalad.tools.functional.testing.layout.ProjectDescriptor

class AndroidAppProjectDescriptor(
    private val name: String,
    private val androidBlockItems: List<AndroidBlockItem> = emptyList(),
    private val dependencies: List<String> = emptyList()
) : ProjectDescriptor() {

    override fun getBuildGradleContents(): String {
        return """
            apply plugin: 'com.android.application'
            apply plugin: 'placeholder-resolver'
            
            android {
                compileSdkVersion 28
                
                ${placeAndroidBlockItems()}
            }
            
            ${getDependenciesBlock()}
        """.trimIndent()
    }

    private fun placeAndroidBlockItems(): String {
        if (androidBlockItems.isEmpty()) {
            return ""
        }

        return androidBlockItems.map { it.getItemText() }
            .fold("") { accumulated, current ->
                "$accumulated\n$current"
            }
    }

    private fun getDependenciesBlock(): String {
        if (dependencies.isEmpty()) {
            return ""
        }

        return """
            dependencies {
                ${getDependenciesListed()}
            }
        """.trimIndent()
    }

    private fun getDependenciesListed(): String {
        return dependencies.fold("") { accumulated, current ->
            "$accumulated\n$current"
        }
    }

    override fun getProjectName(): String = name
}