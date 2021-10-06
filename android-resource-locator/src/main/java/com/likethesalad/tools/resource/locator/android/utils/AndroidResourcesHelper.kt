package com.likethesalad.tools.resource.locator.android.utils

import com.likethesalad.tools.android.plugin.data.AndroidVariantData
import org.gradle.api.Action
import org.gradle.api.artifacts.ArtifactView
import org.gradle.api.attributes.Attribute
import org.gradle.api.file.FileCollection

object AndroidResourcesHelper {

    private val artifactTypeAttr = Attribute.of("artifactType", String::class.java)

    fun getLibrariesResourceFileCollection(variant: AndroidVariantData): FileCollection {
        return variant.getRuntimeConfiguration().incoming
            .artifactView(getResArtifactViewAction())
            .artifacts
            .artifactFiles
    }

    private fun getResArtifactViewAction(): Action<ArtifactView.ViewConfiguration> {
        return Action { config ->
            config.isLenient = false
            config.attributes {
                it.attribute(artifactTypeAttr, "android-res")
            }
        }
    }
}