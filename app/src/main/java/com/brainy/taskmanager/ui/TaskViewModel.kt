package com.brainy.taskmanager.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.brainy.taskmanager.data.Priority
import com.brainy.taskmanager.data.Task
import com.brainy.taskmanager.data.TaskRepository
import com.brainy.taskmanager.notification.TaskReminderScheduler
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch

/**
 * Filter options for displaying tasks in the UI.
 */
enum class FilterType {
    ALL,
    PENDING,
    COMPLETED
}

/**
 * ViewModel for managing task data, filters, statistics, and reminder scheduling.
 */
class TaskViewModel(
    private val repository: TaskRepository,
    application: Application
) : AndroidViewModel(application) {

    private val reminderScheduler = TaskReminderScheduler(getApplication<Application>())

    private val _filterType = MutableStateFlow(FilterType.ALL)
    val filterType: StateFlow<FilterType> = _filterType.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val tasks: LiveData<List<Task>> = _filterType.flatMapLatest { filter ->
        when (filter) {
            FilterType.ALL -> repository.getAllTasks()
            FilterType.PENDING -> repository.getPendingTasks()
            FilterType.COMPLETED -> repository.getCompletedTasks()
        }
    }.asLiveData()

    val totalCount: LiveData<Int> = repository.getTotalCount().asLiveData()
    val pendingCount: LiveData<Int> = repository.getPendingCount().asLiveData()
    val completedCount: LiveData<Int> = repository.getCompletedCount().asLiveData()

    val overdueCount: LiveData<Int> = tasks.switchMap {
        repository.getOverdueCount(System.currentTimeMillis()).asLiveData()
    }

    /**
     * Updates the current task filter (ALL, PENDING, COMPLETED).
     */
    fun setFilter(filterType: FilterType) {
        _filterType.value = filterType
    }

    /**
     * Gets the currently selected filter type.
     */
    fun getCurrentFilter(): FilterType = _filterType.value

    /**
     * Searches tasks by title query.
     */
    fun searchTasks(query: String): LiveData<List<Task>> {
        return repository.searchTasks(query).asLiveData()
    }

    /**
     * Inserts a new task and triggers reminder scheduling if reminder is enabled.
     * Calls [onResult] with the newly generated task ID.
     */
    fun insertTask(task: Task, onResult: (Long) -> Unit = {}) {
        viewModelScope.launch {
            val newId = repository.insertTask(task)
            if (task.reminderEnabled && !task.isCompleted && task.dueDateTime != null) {
                reminderScheduler.scheduleReminder(task.copy(id = newId.toInt()))
            }
            onResult(newId)
        }
    }

    /**
     * Updates an existing task and updates reminder scheduling accordingly.
     */
    fun updateTask(task: Task) {
        viewModelScope.launch {
            repository.updateTask(task)
            if (task.reminderEnabled && !task.isCompleted && task.dueDateTime != null) {
                reminderScheduler.scheduleReminder(task)
            } else {
                reminderScheduler.cancelReminder(task.id)
            }
        }
    }

    /**
     * Deletes a task and cancels any associated reminder.
     */
    fun deleteTask(task: Task) {
        viewModelScope.launch {
            repository.deleteTask(task)
            reminderScheduler.cancelReminder(task.id)
        }
    }

    /**
     * Deletes a task by its ID and cancels any associated reminder.
     */
    fun deleteTaskById(taskId: Int) {
        viewModelScope.launch {
            repository.deleteTaskById(taskId)
            reminderScheduler.cancelReminder(taskId)
        }
    }

    /**
     * Toggles the completion status of a task.
     * - If marked complete: cancels any pending reminder.
     * - If marked incomplete: re-schedules the reminder if enabled and due date is in the future.
     */
    fun toggleTaskCompletion(task: Task) {
        viewModelScope.launch {
            val newCompletedState = !task.isCompleted
            val updatedTask = task.copy(isCompleted = newCompletedState)
            repository.updateTask(updatedTask)

            if (newCompletedState) {
                reminderScheduler.cancelReminder(task.id)
            } else {
                if (updatedTask.reminderEnabled &&
                    updatedTask.dueDateTime != null &&
                    updatedTask.dueDateTime > System.currentTimeMillis()
                ) {
                    reminderScheduler.scheduleReminder(updatedTask)
                }
            }
        }
    }

    /**
     * Retrieves a task by its ID asynchronously.
     */
    fun getTaskById(taskId: Int, callback: (Task?) -> Unit) {
        viewModelScope.launch {
            val task = repository.getTaskById(taskId)
            callback(task)
        }
    }
}
