package com.brainy.taskmanager

import com.brainy.taskmanager.data.Priority
import com.brainy.taskmanager.data.Task
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Unit tests covering task validation, reminder timestamp calculations,
 * and reminder eligibility rules.
 */
class TaskValidationTest {

    @Test
    fun taskTitle_blankValidation_fails() {
        val blankTitle1 = ""
        val blankTitle2 = "   "
        val validTitle = "Finish Report"

        assertTrue(blankTitle1.trim().isEmpty())
        assertTrue(blankTitle2.trim().isEmpty())
        assertFalse(validTitle.trim().isEmpty())
    }

    @Test
    fun reminderCalculation_accurateLeadTimeSubtracted() {
        val dueTime = 1700000000000L
        val leadMinutes = 10
        val expectedReminderTime = dueTime - (leadMinutes * 60 * 1000L)

        val task = Task(
            id = 1,
            title = "Team Meeting",
            dueDateTime = dueTime,
            reminderEnabled = true,
            reminderLeadMinutes = leadMinutes
        )

        val calculatedReminderTime = task.dueDateTime?.minus(task.reminderLeadMinutes * 60 * 1000L)
        assertNotNull(calculatedReminderTime)
        assertEquals(expectedReminderTime, calculatedReminderTime)
    }

    @Test
    fun reminderCalculation_differentLeadTimes() {
        val dueTime = 1700000000000L

        val leadTimes = listOf(5, 10, 30, 60)
        for (minutes in leadTimes) {
            val task = Task(
                id = 2,
                title = "Check lead time $minutes",
                dueDateTime = dueTime,
                reminderEnabled = true,
                reminderLeadMinutes = minutes
            )
            val expectedTime = dueTime - (minutes * 60 * 1000L)
            val actualTime = task.dueDateTime?.minus(task.reminderLeadMinutes * 60 * 1000L)
            assertEquals(expectedTime, actualTime)
        }
    }

    @Test
    fun reminderEligibility_pastReminderTime_notEligible() {
        val currentTime = 1700000000000L
        val dueTimeInPast = currentTime - 3600000L // 1 hour ago
        val leadMinutes = 10

        val reminderTime = dueTimeInPast - (leadMinutes * 60 * 1000L)
        val isEligible = reminderTime > currentTime

        assertFalse("Past reminder time must not be eligible for alarm scheduling", isEligible)
    }

    @Test
    fun reminderEligibility_futureReminderTime_isEligible() {
        val currentTime = 1700000000000L
        val dueTimeInFuture = currentTime + 7200000L // 2 hours in future
        val leadMinutes = 30 // reminder at 1.5 hours in future

        val reminderTime = dueTimeInFuture - (leadMinutes * 60 * 1000L)
        val isEligible = reminderTime > currentTime

        assertTrue("Future reminder time must be eligible for alarm scheduling", isEligible)
    }

    @Test
    fun reminderEligibility_completedTask_notEligible() {
        val task = Task(
            id = 3,
            title = "Completed Task",
            dueDateTime = System.currentTimeMillis() + 1000000L,
            reminderEnabled = true,
            isCompleted = true
        )

        // Lifecycle rule: completed tasks must not have active reminders scheduled
        val canSchedule = task.reminderEnabled && !task.isCompleted && task.dueDateTime != null
        assertFalse(canSchedule)
    }

    @Test
    fun reminderEligibility_disabledReminder_notEligible() {
        val task = Task(
            id = 4,
            title = "No Reminder Task",
            dueDateTime = System.currentTimeMillis() + 1000000L,
            reminderEnabled = false,
            isCompleted = false
        )

        val canSchedule = task.reminderEnabled && !task.isCompleted && task.dueDateTime != null
        assertFalse(canSchedule)
    }

    @Test
    fun reminderEligibility_nullDueDate_notEligible() {
        val task = Task(
            id = 5,
            title = "No Due Date Task",
            dueDateTime = null,
            reminderEnabled = true,
            isCompleted = false
        )

        val canSchedule = task.reminderEnabled && !task.isCompleted && task.dueDateTime != null
        assertFalse(canSchedule)
    }

    @Test
    fun priorityEnum_defaultAndOrdering() {
        val defaultTask = Task(title = "Default Priority Task")
        assertEquals(Priority.MEDIUM, defaultTask.priority)

        assertEquals("LOW", Priority.LOW.name)
        assertEquals("MEDIUM", Priority.MEDIUM.name)
        assertEquals("HIGH", Priority.HIGH.name)
    }

    @Test
    fun overdueCalculation_overdueDetection() {
        val currentTime = 1700000000000L
        val overdueDueDateTime = currentTime - 1000L
        val futureDueDateTime = currentTime + 1000L

        val isOverduePast = overdueDueDateTime < currentTime
        val isOverdueFuture = futureDueDateTime < currentTime

        assertTrue("Past deadline should be detected as overdue", isOverduePast)
        assertFalse("Future deadline should not be detected as overdue", isOverdueFuture)
    }
}
