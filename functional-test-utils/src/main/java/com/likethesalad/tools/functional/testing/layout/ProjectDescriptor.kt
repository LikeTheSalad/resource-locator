package com.likethesalad.tools.functional.testing.layout

abstract class ProjectDescriptor {

    val projectDirectoryBuilder = ProjectDirectoryBuilder()

    abstract fun getBuildGradleContents(): String

    abstract fun getProjectName(): String
}