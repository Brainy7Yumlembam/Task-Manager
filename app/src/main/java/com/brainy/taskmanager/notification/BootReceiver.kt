package com.brainy.taskmanager.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.brainy.taskmanager.data.TaskDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BootReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action != Intent.ACTION_BOOT_COMPLETED) return

        val pendingResult = goAsync()
        
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val database = TaskDatabase.getDatabase(context)
                val dao = database.taskDao()
                val scheduler = TaskReminderScheduler(context)
                
                val eligibleTasks = dao.getTasksWithPendingReminders(System.currentTimeMillis())
                for (task in eligibleTasks) {
                    scheduler.scheduleReminder(task)
                }
            } finally {
                pendingResult.finish()
            }
        }
    }
}
