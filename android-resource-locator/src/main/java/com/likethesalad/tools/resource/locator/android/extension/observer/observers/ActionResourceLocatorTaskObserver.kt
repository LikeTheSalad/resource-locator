package com.likethesalad.tools.resource.locator.android.extension.observer.observers

import com.likethesalad.tools.resource.locator.android.extension.observer.ResourceLocatorTaskObserver
import com.likethesalad.tools.resource.locator.android.extension.observer.data.ResourceLocatorTaskContainer
import org.gradle.api.Action

class ActionResourceLocatorTaskObserver(private val action: Action<ResourceLocatorTaskContainer>) :
    ResourceLocatorTaskObserver {

    override fun notify(taskContainer: ResourceLocatorTaskContainer) {
        action.execute(taskContainer)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ActionResourceLocatorTaskObserver) return false

        if (action != other.action) return false

        return true
    }

    override fun hashCode(): Int {
        return action.hashCode()
    }
}