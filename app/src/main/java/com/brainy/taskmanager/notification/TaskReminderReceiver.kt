package com.brainy.taskmanager.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class TaskReminderReceiver : BroadcastReceiver() {

    companion object {
        const val EXTRA_TASK_ID = "extra_task_id"
        const val EXTRA_TASK_TITLE = "extra_task_title"
        const val EXTRA_TASK_DESCRIPTION = "extra_task_description"
        const val EXTRA_TASK_DUE_DATE_TIME = "extra_task_due_date_time"
    }

    override fun onReceive(context: Context, intent: Intent) {
        val taskId = intent.getIntExtra(EXTRA_TASK_ID, -1)
        if (taskId == -1) return

        val title = intent.getStringExtra(EXTRA_TASK_TITLE) ?: "Task Reminder"
        val description = intent.getStringExtra(EXTRA_TASK_DESCRIPTION) ?: ""
        val dueDateTime = intent.getLongExtra(EXTRA_TASK_DUE_DATE_TIME, -1L)
            .takeIf { it != -1L }

        NotificationHelper.showTaskReminderNotification(
            context = context,
            taskId = taskId,
            title = title,
            description = description,
            dueDateTime = dueDateTime
        )
    }
}
