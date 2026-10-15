package com.brainy.taskmanager.data

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Priority levels for tasks.
 */
enum class Priority {
    LOW,
    MEDIUM,
    HIGH
}

/**
 * Room Entity representing a task in the `tasks` table.
 */
@Entity(
    tableName = "tasks",
    indices = [
        Index(value = ["isCompleted"]),
        Index(value = ["dueDateTime"]),
        Index(value = ["dueDate"]),
        Index(value = ["priority"])
    ]
)
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val description: String = "",
    val dueDate: String? = null,
    val dueDateTime: Long? = null,
    val priority: Priority = Priority.MEDIUM,
    val isCompleted: Boolean = false,
    val reminderEnabled: Boolean = false,
    val reminderLeadMinutes: Int = 10,
    val createdAt: Long = System.currentTimeMillis()
)
