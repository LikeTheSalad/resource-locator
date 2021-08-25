package com.likethesalad.tools.resource.locator.android.extension.observer

class ResourceLocatorTaskPublisher {

    private val observers = mutableListOf<ResourceLocatorTaskObserver>()

    fun register(observer: ResourceLocatorTaskObserver) {
        if (observers.contains(observer)) {
            throw IllegalArgumentException("Observer already registered")
        }
        observers.add(observer)
    }

    fun notify(taskContainer: ResourceLocatorTaskContainer) {
        observers.forEach {
            it.notify(taskContainer)
        }
    }
}