package com.likethesalad.tools.resource.api.android.data

import com.likethesalad.tools.resource.api.android.impl.AndroidResourceType
import com.likethesalad.tools.resource.api.android.testutils.BaseSealedParseableTest

class AndroidResourceTypeTest : BaseSealedParseableTest<AndroidResourceType>(AndroidResourceType::class) {

    override fun getUnknownType(): Class<out AndroidResourceType> {
        return AndroidResourceType.Unknown::class.java
    }
}