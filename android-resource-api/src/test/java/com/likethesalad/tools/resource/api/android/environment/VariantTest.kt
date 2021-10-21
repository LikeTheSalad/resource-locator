package com.likethesalad.tools.resource.api.android.environment

import com.likethesalad.tools.resource.api.android.testutils.BaseSealedParseableTest

class VariantTest : BaseSealedParseableTest<Variant>(Variant::class) {

    override fun getUnknownType(): Class<out Variant> {
        return Variant.Custom::class.java
    }
}