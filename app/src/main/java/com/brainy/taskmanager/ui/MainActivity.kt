package com.brainy.taskmanager.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.brainy.taskmanager.R
import com.brainy.taskmanager.TaskManagerApp
import com.brainy.taskmanager.data.Task
import com.brainy.taskmanager.notification.TaskReminderScheduler
import com.google.android.material.chip.ChipGroup
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

/**
 * Main Activity of the Task Management application.
 * Displays dashboard summary counters, filterable task list, and provides access to task operations.
 */
class MainActivity : AppCompatActivity(), TaskClickListener {

    private lateinit var viewModel: TaskViewModel
    private lateinit var taskAdapter: TaskAdapter
    private lateinit var reminderScheduler: TaskReminderScheduler

    private lateinit var tvTotalCount: TextView
    private lateinit var tvPendingCount: TextView
    private lateinit var tvCompletedCount: TextView
    private lateinit var tvOverdueCount: TextView
    private lateinit var tvGreeting: TextView
    private lateinit var tvTodayDate: TextView
    private lateinit var chipGroupFilter: ChipGroup
    private lateinit var recyclerViewTasks: RecyclerView
    private lateinit var emptyStateLayout: View
    private lateinit var tvEmptyTitle: TextView
    private lateinit var tvEmptyMessage: TextView
    private lateinit var fabAddTask: FloatingActionButton

    private var currentFilter: FilterType = FilterType.ALL

    private val addEditTaskLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        // Task list automatically updates via LiveData observation from Room
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initDependencies()
        initViews()
        setupToolbar()
        setupHeader()
        setupRecyclerView()
        setupFilterChips()
        setupFab()
        observeViewModel()
        requestNotificationPermission()
    }

    private fun initDependencies() {
        val app = application as TaskManagerApp
        val factory = TaskViewModelFactory(app.repository, app)
        viewModel = ViewModelProvider(this, factory)[TaskViewModel::class.java]
        reminderScheduler = TaskReminderScheduler(this)
    }

    private fun initViews() {
        tvTotalCount = findViewById(R.id.tvTotalCount)
        tvPendingCount = findViewById(R.id.tvPendingCount)
        tvCompletedCount = findViewById(R.id.tvCompletedCount)
        tvOverdueCount = findViewById(R.id.tvOverdueCount)
        tvGreeting = findViewById(R.id.tvGreeting)
        tvTodayDate = findViewById(R.id.tvTodayDate)
        chipGroupFilter = findViewById(R.id.chipGroupFilter)
        recyclerViewTasks = findViewById(R.id.recyclerViewTasks)
        emptyStateLayout = findViewById(R.id.emptyStateLayout)
        tvEmptyTitle = findViewById(R.id.tvEmptyTitle)
        tvEmptyMessage = findViewById(R.id.tvEmptyMessage)
        fabAddTask = findViewById(R.id.fabAddTask)
    }

    private fun setupHeader() {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val greetingRes = when (hour) {
            in 5..11 -> R.string.greeting_morning
            in 12..16 -> R.string.greeting_afternoon
            else -> R.string.greeting_evening
        }
        tvGreeting.setText(greetingRes)

        val dateFormat = SimpleDateFormat("EEEE, dd-MMM-yyyy", Locale.ENGLISH)
        tvTodayDate.text = dateFormat.format(Date())
    }

    private fun setupToolbar() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
    }

    private fun setupRecyclerView() {
        taskAdapter = TaskAdapter(this)
        recyclerViewTasks.layoutManager = LinearLayoutManager(this)
        recyclerViewTasks.adapter = taskAdapter
    }

    private fun setupFilterChips() {
        chipGroupFilter.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.chipPending -> {
                    currentFilter = FilterType.PENDING
                    viewModel.setFilter(FilterType.PENDING)
                    updateEmptyStateText(FilterType.PENDING)
                }
                R.id.chipCompleted -> {
                    currentFilter = FilterType.COMPLETED
                    viewModel.setFilter(FilterType.COMPLETED)
                    updateEmptyStateText(FilterType.COMPLETED)
                }
                else -> {
                    currentFilter = FilterType.ALL
                    viewModel.setFilter(FilterType.ALL)
                    updateEmptyStateText(FilterType.ALL)
                }
            }
        }
    }

    private fun setupFab() {
        fabAddTask.setOnClickListener {
            val intent = Intent(this, AddEditTaskActivity::class.java)
            addEditTaskLauncher.launch(intent)
        }
    }

    private fun observeViewModel() {
        viewModel.tasks.observe(this) { tasks ->
            taskAdapter.submitList(tasks)
            val isEmpty = tasks.isNullOrEmpty()
            if (isEmpty) {
                emptyStateLayout.visibility = View.VISIBLE
                recyclerViewTasks.visibility = View.GONE
                updateEmptyStateText(currentFilter)
            } else {
                emptyStateLayout.visibility = View.GONE
                recyclerViewTasks.visibility = View.VISIBLE
            }
        }

        viewModel.totalCount.observe(this) { count ->
            tvTotalCount.text = (count ?: 0).toString()
        }

        viewModel.pendingCount.observe(this) { count ->
            tvPendingCount.text = (count ?: 0).toString()
        }

        viewModel.completedCount.observe(this) { count ->
            tvCompletedCount.text = (count ?: 0).toString()
        }

        viewModel.overdueCount.observe(this) { count ->
            tvOverdueCount.text = (count ?: 0).toString()
        }
    }

    private fun updateEmptyStateText(filterType: FilterType) {
        when (filterType) {
            FilterType.PENDING -> {
                tvEmptyTitle.setText(R.string.no_pending_tasks)
                tvEmptyMessage.setText(R.string.no_pending_tasks_message)
                tvEmptyMessage.visibility = View.VISIBLE
            }
            FilterType.COMPLETED -> {
                tvEmptyTitle.setText(R.string.no_completed_tasks)
                tvEmptyMessage.setText(R.string.no_completed_tasks_message)
                tvEmptyMessage.visibility = View.VISIBLE
            }
            FilterType.ALL -> {
                tvEmptyTitle.setText(R.string.no_tasks)
                tvEmptyMessage.setText(R.string.no_tasks_message)
                tvEmptyMessage.visibility = View.VISIBLE
            }
        }
    }

    // TaskAdapter.TaskClickListener implementation

    override fun onTaskCheckedChanged(task: Task, isChecked: Boolean) {
        viewModel.toggleTaskCompletion(task)
        if (isChecked) {
            reminderScheduler.cancelReminder(task.id)
            Toast.makeText(this, R.string.task_completed, Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, R.string.task_reopened, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onEditClick(task: Task) {
        val intent = Intent(this, AddEditTaskActivity::class.java).apply {
            putExtra(EXTRA_TASK_ID, task.id)
        }
        addEditTaskLauncher.launch(intent)
    }

    override fun onDeleteClick(task: Task) {
        MaterialAlertDialogBuilder(this)
            .setTitle(R.string.confirm_delete)
            .setMessage(R.string.confirm_delete_message)
            .setPositiveButton(R.string.delete_task) { _, _ ->
                viewModel.deleteTask(task)
                reminderScheduler.cancelReminder(task.id)
                Toast.makeText(this, R.string.task_deleted, Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton(R.string.cancel, null)
            .show()
    }

    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    NOTIFICATION_PERMISSION_REQUEST_CODE
                )
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == NOTIFICATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted
            } else {
                // Permission denied
            }
        }
    }

    companion object {
        const val EXTRA_TASK_ID = "task_id"
        private const val NOTIFICATION_PERMISSION_REQUEST_CODE = 1001
    }
}
