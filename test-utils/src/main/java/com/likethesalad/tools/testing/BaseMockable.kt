package com.likethesalad.tools.testing

import io.mockk.MockKAnnotations
import org.junit.Before

open class BaseMockable {

    @Before
    fun setUpMockks() {
        MockKAnnotations.init(this)
    }

    inline fun <reified T : Any> mockk(): T = io.mockk.mockk(relaxUnitFun = true)
}