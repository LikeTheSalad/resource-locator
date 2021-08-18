package com.likethesalad.tools.resource.collector.android.data.variant

import com.likethesalad.tools.resource.api.android.environment.Variant
import com.likethesalad.tools.resource.collector.android.helpers.AndroidVariantHelper

class VariantTree(private val androidVariantHelper: AndroidVariantHelper) {

    private val flavors by lazy {
        androidVariantHelper.getVariantFlavors()
    }

    private val variantName by lazy {
        androidVariantHelper.getVariantName()
    }

    private val variantType by lazy {
        androidVariantHelper.getVariantType()
    }

    private val variantNames = mutableListOf<String>()

    init {
        variantNames.add(Variant.Default.name)
        addFlavorPaths()
        addVariantNameWithoutType()
        addVariantType()
        addFullVariantName()
    }

    private val variantList: List<Variant> by lazy {
        variantNames.map { Variant.Custom(it) }
    }

    fun getVariants(): List<Variant> = variantList

    private fun addFullVariantName() {
        if (flavors.isNotEmpty()) {
            variantNames.add(variantName)
        }
    }

    private fun addVariantType() {
        variantNames.add(variantType)
    }

    private fun addVariantNameWithoutType() {
        getMergedFlavorsLowerCamelCase()?.let {
            variantNames.add(it)
        }
    }

    private fun addFlavorPaths() {
        for (it in flavors.reversed()) {
            variantNames.add(it)
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

    fun check(subject: Variant): Comparator {
        return Comparator(subject)
    }

    inner class Comparator constructor(subject: Variant) {
        private val subjectIndex = getIndexOf(subject)

        fun isChildOf(variant: Variant): Boolean {
            val otherIndex = getIndexOf(variant)
            return otherIndex < subjectIndex
        }

        private fun getIndexOf(variant: Variant): Int {
            return variantList.indexOf(variant)
        }
    }
}