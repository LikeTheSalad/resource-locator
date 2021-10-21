package com.likethesalad.tools.resource.api.android.environment

import com.likethesalad.tools.resource.api.android.testutils.BaseSealedParseableTest

class LanguageTest : BaseSealedParseableTest<Language>(Language::class) {

    override fun getUnknownType(): Class<out Language> {
        return Language.Custom::class.java
    }
}