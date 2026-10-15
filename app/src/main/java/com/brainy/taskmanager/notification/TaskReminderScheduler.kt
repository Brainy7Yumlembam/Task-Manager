package com.brainy.taskmanager.notification

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import com.brainy.taskmanager.data.Task

class TaskReminderScheduler(private val context: Context) {

    private val alarmManager: AlarmManager =
        context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    /**
     * Schedules a reminder for the given task.
     * The reminder fires at (dueDateTime - reminderLeadMinutes).
     * Will not schedule if:
     * - reminder is disabled
     * - task is completed
     * - dueDateTime is null
     * - reminder time is in the past
     */
    fun scheduleReminder(task: Task) {
        // Cancel any existing reminder first
        cancelReminder(task.id)

        if (!task.reminderEnabled || task.isCompleted || task.dueDateTime == null) {
            return
        }

        val reminderTime = task.dueDateTime - (task.reminderLeadMinutes * 60 * 1000L)
        if (reminderTime <= System.currentTimeMillis()) {
            return // Don't schedule reminders in the past
        }

        val intent = Intent(context, TaskReminderReceiver::class.java).apply {
            putExtra(TaskReminderReceiver.EXTRA_TASK_ID, task.id)
            putExtra(TaskReminderReceiver.EXTRA_TASK_TITLE, task.title)
            putExtra(TaskReminderReceiver.EXTRA_TASK_DESCRIPTION, task.description)
            putExtra(TaskReminderReceiver.EXTRA_TASK_DUE_DATE_TIME, task.dueDateTime)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            task.id,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                // Android 12+ requires checking exact alarm permission
                if (alarmManager.canScheduleExactAlarms()) {
                    alarmManager.setExactAndAllowWhileIdle(
                        AlarmManager.RTC_WAKEUP, reminderTime, pendingIntent
                    )
                } else {
                    // Fall back to inexact alarm
                    alarmManager.setAndAllowWhileIdle(
                        AlarmManager.RTC_WAKEUP, reminderTime, pendingIntent
                    )
                }
            } else {
                alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP, reminderTime, pendingIntent
                )
            }
        } catch (e: SecurityException) {
            // If exact alarm permission denied, try inexact
            alarmManager.setAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP, reminderTime, pendingIntent
            )
        }
    }

    /**
     * Cancels any scheduled reminder for the given task ID.
     */
    fun cancelReminder(taskId: Int) {
        val intent = Intent(context, TaskReminderReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            taskId,
            intent,
            PendingIntent.FLAG_NO_CREATE or PendingIntent.FLAG_IMMUTABLE
        )
        pendingIntent?.let {
            alarmManager.cancel(it)
            it.cancel()
        }
    }
}
