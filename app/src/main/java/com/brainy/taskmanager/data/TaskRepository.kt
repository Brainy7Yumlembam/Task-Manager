package com.brainy.taskmanager.data

import kotlinx.coroutines.flow.Flow

/**
 * Repository providing access to task data from the local Room database.
 */
class TaskRepository(private val taskDao: TaskDao) {


    fun getAllTasks(): Flow<List<Task>> = taskDao.getAllTasks()

    fun getPendingTasks(): Flow<List<Task>> = taskDao.getPendingTasks()

    fun getCompletedTasks(): Flow<List<Task>> = taskDao.getCompletedTasks()

    fun searchTasks(query: String): Flow<List<Task>> = taskDao.searchTasks(query)

    suspend fun getTaskById(taskId: Int): Task? = taskDao.getTaskById(taskId)

    suspend fun insertTask(task: Task): Long = taskDao.insertTask(task)

    suspend fun updateTask(task: Task) = taskDao.updateTask(task)

    suspend fun deleteTask(task: Task) = taskDao.deleteTask(task)

    suspend fun deleteTaskById(taskId: Int) = taskDao.deleteTaskById(taskId)

    fun getTotalCount(): Flow<Int> = taskDao.getTotalCount()

    fun getPendingCount(): Flow<Int> = taskDao.getPendingCount()

    fun getCompletedCount(): Flow<Int> = taskDao.getCompletedCount()

    fun getOverdueCount(currentTime: Long): Flow<Int> = taskDao.getOverdueCount(currentTime)

    suspend fun getTasksWithPendingReminders(currentTime: Long): List<Task> =
        taskDao.getTasksWithPendingReminders(currentTime)
}
