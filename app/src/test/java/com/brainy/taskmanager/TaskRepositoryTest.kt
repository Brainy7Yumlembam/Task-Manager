package com.brainy.taskmanager

import com.brainy.taskmanager.data.Priority
import com.brainy.taskmanager.data.Task
import com.brainy.taskmanager.data.TaskDao
import com.brainy.taskmanager.data.TaskRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`

/**
 * Unit tests verifying TaskRepository operations with mock DAO.
 */
@OptIn(ExperimentalCoroutinesApi::class)
class TaskRepositoryTest {

    private lateinit var taskDao: TaskDao
    private lateinit var repository: TaskRepository

    private val sampleTask1 = Task(
        id = 1,
        title = "Test Task 1",
        description = "Description 1",
        dueDateTime = 1700000000000L,
        priority = Priority.HIGH,
        isCompleted = false,
        reminderEnabled = true,
        reminderLeadMinutes = 15
    )

    private val sampleTask2 = Task(
        id = 2,
        title = "Test Task 2",
        description = "Description 2",
        dueDateTime = 1700003600000L,
        priority = Priority.MEDIUM,
        isCompleted = true,
        reminderEnabled = false,
        reminderLeadMinutes = 10
    )

    @Before
    fun setup() {
        taskDao = mock(TaskDao::class.java)
        repository = TaskRepository(taskDao)
    }

    @Test
    fun getAllTasks_returnsDaoFlow() = runTest {
        val expectedTasks = listOf(sampleTask1, sampleTask2)
        `when`(taskDao.getAllTasks()).thenReturn(flowOf(expectedTasks))

        val result = repository.getAllTasks().first()

        assertEquals(2, result.size)
        assertEquals("Test Task 1", result[0].title)
        assertEquals("Test Task 2", result[1].title)
        verify(taskDao).getAllTasks()
    }

    @Test
    fun getPendingTasks_returnsPendingOnly() = runTest {
        val pendingTasks = listOf(sampleTask1)
        `when`(taskDao.getPendingTasks()).thenReturn(flowOf(pendingTasks))

        val result = repository.getPendingTasks().first()

        assertEquals(1, result.size)
        assertEquals(false, result[0].isCompleted)
        verify(taskDao).getPendingTasks()
    }

    @Test
    fun getCompletedTasks_returnsCompletedOnly() = runTest {
        val completedTasks = listOf(sampleTask2)
        `when`(taskDao.getCompletedTasks()).thenReturn(flowOf(completedTasks))

        val result = repository.getCompletedTasks().first()

        assertEquals(1, result.size)
        assertEquals(true, result[0].isCompleted)
        verify(taskDao).getCompletedTasks()
    }

    @Test
    fun insertTask_delegatesToDaoAndReturnsId() = runTest {
        `when`(taskDao.insertTask(sampleTask1)).thenReturn(1L)

        val newId = repository.insertTask(sampleTask1)

        assertEquals(1L, newId)
        verify(taskDao).insertTask(sampleTask1)
    }

    @Test
    fun updateTask_delegatesToDao() = runTest {
        val updatedTask = sampleTask1.copy(title = "Updated Title")

        repository.updateTask(updatedTask)

        verify(taskDao).updateTask(updatedTask)
    }

    @Test
    fun deleteTask_delegatesToDao() = runTest {
        repository.deleteTask(sampleTask1)

        verify(taskDao).deleteTask(sampleTask1)
    }

    @Test
    fun deleteTaskById_delegatesToDao() = runTest {
        repository.deleteTaskById(1)

        verify(taskDao).deleteTaskById(1)
    }

    @Test
    fun getTaskById_returnsTaskWhenFound() = runTest {
        `when`(taskDao.getTaskById(1)).thenReturn(sampleTask1)
        `when`(taskDao.getTaskById(99)).thenReturn(null)

        val found = repository.getTaskById(1)
        val notFound = repository.getTaskById(99)

        assertNotNull(found)
        assertEquals("Test Task 1", found?.title)
        assertNull(notFound)
    }

    @Test
    fun searchTasks_delegatesToDao() = runTest {
        `when`(taskDao.searchTasks("Task 1")).thenReturn(flowOf(listOf(sampleTask1)))

        val searchResult = repository.searchTasks("Task 1").first()

        assertEquals(1, searchResult.size)
        assertEquals("Test Task 1", searchResult[0].title)
        verify(taskDao).searchTasks("Task 1")
    }

    @Test
    fun dashboardCounts_returnAccurateFlows() = runTest {
        val currentTime = 1700000000000L
        `when`(taskDao.getTotalCount()).thenReturn(flowOf(10))
        `when`(taskDao.getPendingCount()).thenReturn(flowOf(6))
        `when`(taskDao.getCompletedCount()).thenReturn(flowOf(4))
        `when`(taskDao.getOverdueCount(currentTime)).thenReturn(flowOf(2))

        assertEquals(10, repository.getTotalCount().first())
        assertEquals(6, repository.getPendingCount().first())
        assertEquals(4, repository.getCompletedCount().first())
        assertEquals(2, repository.getOverdueCount(currentTime).first())
    }
}
