package com.brainy.taskmanager.data

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

/**
 * Instrumented Room Database tests verifying all DAO queries, CRUD operations,
 * filters (All, Pending, Completed), title search, and dashboard count calculations.
 */
@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class TaskDatabaseTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: TaskDatabase
    private lateinit var taskDao: TaskDao

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, TaskDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        taskDao = database.taskDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        database.close()
    }

    @Test
    fun insertAndGetTaskById() = runTest {
        val task = Task(
            title = "Test Task",
            description = "Test Description",
            dueDateTime = 1700000000000L,
            priority = Priority.HIGH,
            isCompleted = false,
            reminderEnabled = true,
            reminderLeadMinutes = 10
        )
        val id = taskDao.insertTask(task).toInt()

        val loaded = taskDao.getTaskById(id)
        assertNotNull(loaded)
        assertEquals("Test Task", loaded?.title)
        assertEquals("Test Description", loaded?.description)
        assertEquals(Priority.HIGH, loaded?.priority)
        assertEquals(false, loaded?.isCompleted)
        assertEquals(true, loaded?.reminderEnabled)
        assertEquals(10, loaded?.reminderLeadMinutes)
    }

    @Test
    fun updateTask() = runTest {
        val task = Task(title = "Original Title", priority = Priority.LOW)
        val id = taskDao.insertTask(task).toInt()

        val inserted = taskDao.getTaskById(id)!!
        val updated = inserted.copy(title = "Updated Title", priority = Priority.HIGH, isCompleted = true)
        taskDao.updateTask(updated)

        val retrieved = taskDao.getTaskById(id)
        assertNotNull(retrieved)
        assertEquals("Updated Title", retrieved?.title)
        assertEquals(Priority.HIGH, retrieved?.priority)
        assertTrue(retrieved?.isCompleted == true)
    }

    @Test
    fun deleteTask() = runTest {
        val task = Task(title = "To Delete")
        val id = taskDao.insertTask(task).toInt()

        val inserted = taskDao.getTaskById(id)!!
        taskDao.deleteTask(inserted)

        val retrieved = taskDao.getTaskById(id)
        assertNull(retrieved)
    }

    @Test
    fun deleteTaskById() = runTest {
        val task = Task(title = "To Delete By ID")
        val id = taskDao.insertTask(task).toInt()

        taskDao.deleteTaskById(id)

        val retrieved = taskDao.getTaskById(id)
        assertNull(retrieved)
    }

    @Test
    fun filterPendingAndCompletedTasks() = runTest {
        taskDao.insertTask(Task(title = "Pending 1", isCompleted = false))
        taskDao.insertTask(Task(title = "Completed 1", isCompleted = true))
        taskDao.insertTask(Task(title = "Pending 2", isCompleted = false))

        val pending = taskDao.getPendingTasks().first()
        val completed = taskDao.getCompletedTasks().first()
        val all = taskDao.getAllTasks().first()

        assertEquals(2, pending.size)
        assertTrue(pending.all { !it.isCompleted })

        assertEquals(1, completed.size)
        assertTrue(completed.all { it.isCompleted })

        assertEquals(3, all.size)
    }

    @Test
    fun searchTasksByTitle() = runTest {
        taskDao.insertTask(Task(title = "Buy groceries"))
        taskDao.insertTask(Task(title = "Call doctor"))
        taskDao.insertTask(Task(title = "Buy books"))

        val searchGroceries = taskDao.searchTasks("groceries").first()
        val searchBuy = taskDao.searchTasks("Buy").first()
        val searchEmpty = taskDao.searchTasks("Gym").first()

        assertEquals(1, searchGroceries.size)
        assertEquals("Buy groceries", searchGroceries[0].title)

        assertEquals(2, searchBuy.size)
        assertEquals(0, searchEmpty.size)
    }

    @Test
    fun dashboardCounts_accurateCalculations() = runTest {
        val now = 1700000000000L
        val pastDue = now - 3600000L
        val futureDue = now + 3600000L

        // Task 1: Pending & Overdue
        taskDao.insertTask(Task(title = "Overdue Task", isCompleted = false, dueDateTime = pastDue))
        // Task 2: Pending & Future
        taskDao.insertTask(Task(title = "Upcoming Task", isCompleted = false, dueDateTime = futureDue))
        // Task 3: Completed (even if past due, completed tasks are not counted as overdue)
        taskDao.insertTask(Task(title = "Completed Past Task", isCompleted = true, dueDateTime = pastDue))
        // Task 4: Pending without due date
        taskDao.insertTask(Task(title = "No Due Date Task", isCompleted = false, dueDateTime = null))

        val total = taskDao.getTotalCount().first()
        val pending = taskDao.getPendingCount().first()
        val completed = taskDao.getCompletedCount().first()
        val overdue = taskDao.getOverdueCount(now).first()

        assertEquals(4, total)
        assertEquals(3, pending)
        assertEquals(1, completed)
        assertEquals(1, overdue)
    }

    @Test
    fun getTasksWithPendingReminders_onlyEligibleTasks() = runTest {
        val now = 1700000000000L
        val pastDue = now - 3600000L
        val futureDue = now + 3600000L

        // Task 1: Eligible (pending, reminder on, future deadline)
        taskDao.insertTask(Task(title = "Eligible", isCompleted = false, reminderEnabled = true, dueDateTime = futureDue))
        // Task 2: Ineligible - completed
        taskDao.insertTask(Task(title = "Completed", isCompleted = true, reminderEnabled = true, dueDateTime = futureDue))
        // Task 3: Ineligible - reminder disabled
        taskDao.insertTask(Task(title = "Reminder off", isCompleted = false, reminderEnabled = false, dueDateTime = futureDue))
        // Task 4: Ineligible - deadline past
        taskDao.insertTask(Task(title = "Past deadline", isCompleted = false, reminderEnabled = true, dueDateTime = pastDue))

        val eligible = taskDao.getTasksWithPendingReminders(now)

        assertEquals(1, eligible.size)
        assertEquals("Eligible", eligible[0].title)
    }
}
