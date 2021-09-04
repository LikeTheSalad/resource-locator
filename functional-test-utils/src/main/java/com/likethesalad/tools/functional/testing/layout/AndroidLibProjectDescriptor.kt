package com.likethesalad.tools.functional.testing.layout

class AndroidLibProjectDescriptor(private val name: String) : ProjectDescriptor() {

    override fun getBuildGradleContents(): String {
        return """
            apply plugin: 'com.android.library'
            
            android {
                compileSdkVersion = 28
            }
        """.trimIndent()
    }

    override fun getProjectName(): String = name
}