package com.likethesalad.tools.resource.locator.android.providers

import org.gradle.api.Task
import org.gradle.api.tasks.TaskProvider

interface TaskFinder {
    fun findTaskByName(name: String): TaskProvider<Task>
}