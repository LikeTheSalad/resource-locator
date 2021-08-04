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

    fun compare(comparable: Variant): Comparator {
        TODO("Not yet implemented")
    }

    class Comparator private constructor(
        private val comparable: Variant,
        private val variantsInOrder: List<Variant>
    ) {
        fun isParentOf(variant: Variant): Boolean {
            TODO("Not yet implemented")
        }

        fun isChildOf(variant: Variant): Boolean {
            TODO("Not yet implemented")
        }

        enum class Comparison {
            EQUAL,
            PARENT,
            CHILD,
            UNKNOWN
        }
    }
}