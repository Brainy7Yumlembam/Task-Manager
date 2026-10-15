package com.brainy.taskmanager.ui

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.brainy.taskmanager.data.TaskRepository

/**
 * Factory for creating [TaskViewModel] instances with [TaskRepository] and [Application] dependencies.
 */
class TaskViewModelFactory(
    private val repository: TaskRepository,
    private val application: Application
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TaskViewModel::class.java)) {
            return TaskViewModel(repository, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}
