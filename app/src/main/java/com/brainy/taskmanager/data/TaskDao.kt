package com.brainy.taskmanager.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for the `tasks` table.
 */
@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: Task): Long

    @Update
    suspend fun updateTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)

    @Query("DELETE FROM tasks WHERE id = :taskId")
    suspend fun deleteTaskById(taskId: Int)

    @Query("SELECT * FROM tasks WHERE id = :taskId")
    suspend fun getTaskById(taskId: Int): Task?

    @Query("SELECT * FROM tasks ORDER BY isCompleted ASC, CASE WHEN dueDateTime IS NULL THEN 1 ELSE 0 END, dueDateTime ASC")
    fun getAllTasks(): Flow<List<Task>>

    @Query("SELECT * FROM tasks WHERE isCompleted = 0 ORDER BY CASE WHEN dueDateTime IS NULL THEN 1 ELSE 0 END, dueDateTime ASC")
    fun getPendingTasks(): Flow<List<Task>>

    @Query("SELECT * FROM tasks WHERE isCompleted = 1 ORDER BY dueDateTime DESC")
    fun getCompletedTasks(): Flow<List<Task>>

    @Query("SELECT * FROM tasks WHERE title LIKE '%' || :query || '%' ORDER BY isCompleted ASC, CASE WHEN dueDateTime IS NULL THEN 1 ELSE 0 END, dueDateTime ASC")
    fun searchTasks(query: String): Flow<List<Task>>

    @Query("SELECT COUNT(*) FROM tasks")
    fun getTotalCount(): Flow<Int>

    @Query("SELECT COUNT(*) FROM tasks WHERE isCompleted = 0")
    fun getPendingCount(): Flow<Int>

    @Query("SELECT COUNT(*) FROM tasks WHERE isCompleted = 1")
    fun getCompletedCount(): Flow<Int>

    @Query("SELECT COUNT(*) FROM tasks WHERE isCompleted = 0 AND dueDateTime IS NOT NULL AND dueDateTime < :currentTime")
    fun getOverdueCount(currentTime: Long): Flow<Int>

    @Query("SELECT * FROM tasks WHERE isCompleted = 0 AND reminderEnabled = 1 AND dueDateTime IS NOT NULL AND dueDateTime > :currentTime")
    suspend fun getTasksWithPendingReminders(currentTime: Long): List<Task>
}
