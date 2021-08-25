package com.likethesalad.tools.resource.locator.android.extension.observer

import com.google.common.truth.Truth
import com.likethesalad.tools.testing.BaseMockable
import io.mockk.verify
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class ResourceLocatorTaskPublisherTest : BaseMockable() {

    private lateinit var publisher: ResourceLocatorTaskPublisher

    @Before
    fun setUp() {
        publisher = ResourceLocatorTaskPublisher()
    }

    @Test
    fun `Notify registered observers`() {
        val observer = mockk<ResourceLocatorTaskObserver>()
        val taskContainer = mockk<ResourceLocatorTaskContainer>()
        publisher.register(observer)

        publisher.notify(taskContainer)

        verify { observer.notify(taskContainer) }
    }

    @Test
    fun `Throw error when trying to register an already existing observer`() {
        val observer = mockk<ResourceLocatorTaskObserver>()
        publisher.register(observer)

        try {
            publisher.register(observer)
            Assert.fail()
        } catch (e: IllegalArgumentException) {
            Truth.assertThat(e.message).isEqualTo("Observer already registered")
        }
    }
}