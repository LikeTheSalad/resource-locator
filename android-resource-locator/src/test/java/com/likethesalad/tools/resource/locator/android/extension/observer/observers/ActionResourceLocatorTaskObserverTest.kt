package com.likethesalad.tools.resource.locator.android.extension.observer.observers

import com.google.common.truth.Truth
import com.likethesalad.tools.resource.locator.android.extension.observer.ResourceLocatorTaskContainer
import com.likethesalad.tools.testing.BaseMockable
import io.mockk.verify
import org.gradle.api.Action
import org.junit.Test

class ActionResourceLocatorTaskObserverTest : BaseMockable() {

    @Test
    fun `Notifying Action`() {
        val action = mockk<Action<ResourceLocatorTaskContainer>>()
        val taskContainer = mockk<ResourceLocatorTaskContainer>()
        val observer = ActionResourceLocatorTaskObserver(action)

        observer.notify(taskContainer)

        verify { action.execute(taskContainer) }
    }

    @Test
    fun `Check equals`() {
        val action1 = mockk<Action<ResourceLocatorTaskContainer>>()
        val action2 = mockk<Action<ResourceLocatorTaskContainer>>()
        val observer1 = ActionResourceLocatorTaskObserver(action1)
        val observer2 = ActionResourceLocatorTaskObserver(action2)
        val observer3 = ActionResourceLocatorTaskObserver(action2)

        Truth.assertThat(observer1).isNotEqualTo(observer2)
        Truth.assertThat(observer1).isNotEqualTo(observer3)
        Truth.assertThat(observer2).isEqualTo(observer3)
    }
}