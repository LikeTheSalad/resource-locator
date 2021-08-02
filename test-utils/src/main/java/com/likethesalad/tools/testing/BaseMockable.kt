package com.likethesalad.tools.testing

import io.mockk.MockKAnnotations
import org.junit.Before

class BaseMockable {

    @Before
    fun setUpMockks() {
        MockKAnnotations.init(this)
    }
}