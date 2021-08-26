package com.likethesalad.tools.resource.locator.android.extension.observer

import com.likethesalad.tools.resource.locator.android.extension.observer.data.ResourceLocatorTaskContainer

interface ResourceLocatorTaskObserver {
    fun notify(taskContainer: ResourceLocatorTaskContainer)
}
