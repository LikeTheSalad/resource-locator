package com.likethesalad.tools.resource.collector.android.data.variant

import com.likethesalad.tools.resource.collector.android.helpers.AndroidVariantHelper

//todo to be used as reference for VariantTree
class VariantDirsPathResolver(
    private val appVariantHelper: AndroidVariantHelper
) {

    companion object {
        const val BASE_DIR_PATH = "main"
    }

    private val flavors by lazy {
        appVariantHelper.getVariantFlavors()
    }

    private val variantName by lazy {
        appVariantHelper.getVariantName()
    }

    private val variantType by lazy {
        appVariantHelper.getVariantType()
    }

    val pathList: List<String> by lazy {
        val pathList = mutableListOf<String>()
        pathList.add(BASE_DIR_PATH)
        addFlavorPaths(pathList)
        addVariantNameWithoutType(pathList)
        addVariantType(pathList)
        addFullVariantName(pathList)
        pathList
    }

    private fun addFullVariantName(pathList: MutableList<String>) {
        if (flavors.isNotEmpty()) {
            pathList.add(variantName)
        }
    }

    private fun addVariantType(pathList: MutableList<String>) {
        pathList.add(variantType)
    }

    private fun addVariantNameWithoutType(pathList: MutableList<String>) {
        getMergedFlavorsLowerCamelCase()?.let {
            pathList.add(it)
        }
    }

    private fun addFlavorPaths(pathList: MutableList<String>) {
        for (it in flavors.reversed()) {
            pathList.add(it)
        }
    }

    private fun getMergedFlavorsLowerCamelCase(): String? {
        if (flavors.size < 2) {
            return null
        }
        var result = flavors.first()
        for (it in flavors.drop(1)) {
            result += it.capitalize()
        }
        return result
    }
}