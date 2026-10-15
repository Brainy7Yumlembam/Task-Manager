package com.brainy.taskmanager

import android.app.Application
import com.brainy.taskmanager.data.TaskDatabase
import com.brainy.taskmanager.data.TaskRepository
import com.brainy.taskmanager.notification.NotificationHelper

class TaskManagerApp : Application() {

    val database: TaskDatabase by lazy { TaskDatabase.getDatabase(this) }
    val repository: TaskRepository by lazy { TaskRepository(database.taskDao()) }

    override fun onCreate() {
        super.onCreate()
        NotificationHelper.createNotificationChannel(this)
    }
}
