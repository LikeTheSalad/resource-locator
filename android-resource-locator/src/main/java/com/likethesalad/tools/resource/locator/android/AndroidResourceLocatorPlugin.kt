package com.likethesalad.tools.resource.locator.android

import com.android.build.gradle.AppExtension
import com.android.build.gradle.api.ApplicationVariant
import com.likethesalad.tools.android.plugin.AndroidVariantHelper
import com.likethesalad.tools.resource.collector.ResourceCollector
import com.likethesalad.tools.resource.collector.android.data.variant.VariantTree
import org.gradle.api.Plugin
import org.gradle.api.Project

abstract class AndroidResourceLocatorPlugin : Plugin<Project>, AndroidVariantHelper {

    private lateinit var project: Project

    override fun apply(project: Project) {
        val android = project.extensions.getByType(AppExtension::class.java)
        val extension =
            project.extensions.create("${getLocatorId()}ResourceLocator", ResourceLocatorExtension::class.java)
        this.project = project
        project.afterEvaluate {
            android.applicationVariants.forEach { variant ->
                createResourceLocatorTaskForVariant(variant)
            }
        }
    }

    private fun createResourceLocatorTaskForVariant(
        androidVariant: ApplicationVariant
    ) {
        val taskName = "${getLocatorId()}${androidVariant.name.toString().capitalize()}"
        val variantTree = VariantTree(this)
        val collector = getResourceCollector(variantTree)
        val task = project.tasks.register(taskName, ResourceLocatorTask::class.java, collector, variantTree)
    }

    abstract fun getLocatorId(): String

    abstract fun getResourceCollector(variantTree: VariantTree): ResourceCollector

    override fun getVariantName(): String {
        TODO("Not yet implemented")
    }

    override fun getVariantType(): String {
        TODO("Not yet implemented")
    }

    override fun getVariantFlavors(): List<String> {
        TODO("Not yet implemented")
    }
}