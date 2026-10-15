package com.brainy.taskmanager.ui

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.format.DateFormat
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CompoundButton
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.ViewModelProvider
import com.brainy.taskmanager.R
import com.brainy.taskmanager.TaskManagerApp
import com.brainy.taskmanager.data.Priority
import com.brainy.taskmanager.data.Task
import com.brainy.taskmanager.notification.TaskReminderScheduler
import com.google.android.material.button.MaterialButton
import com.google.android.material.button.MaterialButtonToggleGroup
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

/**
 * Activity for creating a new task or editing an existing task.
 * Supports setting title, description, due date/time, priority level, and reminders.
 */
class AddEditTaskActivity : AppCompatActivity() {

    private lateinit var viewModel: TaskViewModel
    private lateinit var reminderScheduler: TaskReminderScheduler

    private lateinit var toolbar: Toolbar
    private lateinit var tilTitle: TextInputLayout
    private lateinit var etTitle: TextInputEditText
    private lateinit var tilDescription: TextInputLayout
    private lateinit var etDescription: TextInputEditText
    private lateinit var tilDueDate: TextInputLayout
    private lateinit var etDueDate: TextInputEditText
    private lateinit var tilDueTime: TextInputLayout
    private lateinit var etDueTime: TextInputEditText
    private lateinit var btnClearDate: View
    private lateinit var toggleGroupPriority: MaterialButtonToggleGroup
    private lateinit var switchReminder: CompoundButton
    private lateinit var reminderOptionsLayout: View
    private lateinit var spinnerReminderTime: Spinner
    private lateinit var btnSave: Button

    private var taskId: Int = -1
    private var isEditMode: Boolean = false
    private var existingTask: Task? = null
    private var isSaving: Boolean = false

    private var selectedYear: Int = -1
    private var selectedMonth: Int = -1
    private var selectedDay: Int = -1
    private var selectedHour: Int = -1
    private var selectedMinute: Int = -1

    private val dateFormat = SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH)
    private val timeFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())

    private val reminderOptions = listOf(
        "5 minutes before" to 5,
        "10 minutes before" to 10,
        "30 minutes before" to 30,
        "1 hour before" to 60
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_task)

        initDependencies()
        initViews()
        parseIntentExtras()
        setupToolbar()
        setupDateTimePickers()
        setupPriorityToggle()
        setupReminderSection()
        setupSaveButton()

        if (isEditMode) {
            loadExistingTask()
        }
    }

    private fun initDependencies() {
        val app = application as TaskManagerApp
        val factory = TaskViewModelFactory(app.repository, app)
        viewModel = ViewModelProvider(this, factory)[TaskViewModel::class.java]
        reminderScheduler = TaskReminderScheduler(this)
    }

    private fun initViews() {
        toolbar = findViewById(R.id.toolbar)
        tilTitle = findViewById(R.id.tilTitle)
        etTitle = findViewById(R.id.etTitle)
        tilDescription = findViewById(R.id.tilDescription)
        etDescription = findViewById(R.id.etDescription)
        tilDueDate = findViewById(R.id.tilDueDate)
        etDueDate = findViewById(R.id.etDueDate)
        tilDueTime = findViewById(R.id.tilDueTime)
        etDueTime = findViewById(R.id.etDueTime)
        btnClearDate = findViewById(R.id.btnClearDate)
        toggleGroupPriority = findViewById(R.id.toggleGroupPriority)
        switchReminder = findViewById(R.id.switchReminder)
        reminderOptionsLayout = findViewById(R.id.reminderOptionsLayout)
        spinnerReminderTime = findViewById(R.id.spinnerReminderTime)
        btnSave = findViewById(R.id.btnSave)
        findViewById<View>(R.id.btnCancel)?.setOnClickListener { finish() }
    }

    private fun parseIntentExtras() {
        taskId = intent.getIntExtra(EXTRA_TASK_ID, -1)
        isEditMode = taskId != -1
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        val titleRes = if (isEditMode) R.string.edit_task else R.string.add_task
        toolbar.setTitle(titleRes)
        supportActionBar?.setTitle(titleRes)

        toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun setupDateTimePickers() {
        etDueDate.isFocusable = false
        etDueDate.isClickable = true
        etDueDate.isCursorVisible = false
        etDueDate.setOnClickListener { showDatePicker() }
        tilDueDate.setOnClickListener { showDatePicker() }

        etDueTime.isFocusable = false
        etDueTime.isClickable = true
        etDueTime.isCursorVisible = false
        etDueTime.setOnClickListener { showTimePicker() }
        tilDueTime.setOnClickListener { showTimePicker() }

        btnClearDate.visibility = View.GONE
        btnClearDate.setOnClickListener {
            clearDateTime()
        }
    }

    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val initialYear = if (selectedYear != -1) selectedYear else calendar.get(Calendar.YEAR)
        val initialMonth = if (selectedMonth != -1) selectedMonth else calendar.get(Calendar.MONTH)
        val initialDay = if (selectedDay != -1) selectedDay else calendar.get(Calendar.DAY_OF_MONTH)

        DatePickerDialog(
            this,
            { _, year, month, dayOfMonth ->
                selectedYear = year
                selectedMonth = month
                selectedDay = dayOfMonth
                updateDateDisplay()
                btnClearDate.visibility = View.VISIBLE
            },
            initialYear,
            initialMonth,
            initialDay
        ).show()
    }

    private fun showTimePicker() {
        val calendar = Calendar.getInstance()
        val initialHour = if (selectedHour != -1) selectedHour else calendar.get(Calendar.HOUR_OF_DAY)
        val initialMinute = if (selectedMinute != -1) selectedMinute else calendar.get(Calendar.MINUTE)

        TimePickerDialog(
            this,
            { _, hourOfDay, minute ->
                selectedHour = hourOfDay
                selectedMinute = minute
                updateTimeDisplay()
                btnClearDate.visibility = View.VISIBLE
            },
            initialHour,
            initialMinute,
            DateFormat.is24HourFormat(this)
        ).show()
    }

    private fun updateDateDisplay() {
        if (selectedYear != -1 && selectedMonth != -1 && selectedDay != -1) {
            val calendar = Calendar.getInstance().apply {
                set(selectedYear, selectedMonth, selectedDay)
            }
            etDueDate.setText(dateFormat.format(calendar.time))
        } else {
            etDueDate.setText("")
        }
    }

    private fun updateTimeDisplay() {
        if (selectedHour != -1 && selectedMinute != -1) {
            val calendar = Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, selectedHour)
                set(Calendar.MINUTE, selectedMinute)
            }
            etDueTime.setText(timeFormat.format(calendar.time))
        } else {
            etDueTime.setText("")
        }
    }

    private fun clearDateTime() {
        selectedYear = -1
        selectedMonth = -1
        selectedDay = -1
        selectedHour = -1
        selectedMinute = -1
        etDueDate.setText("")
        etDueTime.setText("")
        btnClearDate.visibility = View.GONE

        if (switchReminder.isChecked) {
            switchReminder.isChecked = false
            reminderOptionsLayout.visibility = View.GONE
        }
    }

    private fun getDueDateTime(): Long? {
        if (selectedYear == -1 || selectedMonth == -1 || selectedDay == -1) return null
        val calendar = Calendar.getInstance()
        calendar.set(
            selectedYear,
            selectedMonth,
            selectedDay,
            if (selectedHour != -1) selectedHour else 0,
            if (selectedMinute != -1) selectedMinute else 0,
            0
        )
        calendar.set(Calendar.MILLISECOND, 0)
        return calendar.timeInMillis
    }

    private fun setupPriorityToggle() {
        toggleGroupPriority.check(R.id.btnPriorityLow)
    }

    private fun setupReminderSection() {
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            reminderOptions.map { it.first }
        ).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
        spinnerReminderTime.adapter = adapter
        spinnerReminderTime.setSelection(1) // Default to 10 minutes before

        switchReminder.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                if (getDueDateTime() == null) {
                    Toast.makeText(this, R.string.set_due_date_first, Toast.LENGTH_SHORT).show()
                    switchReminder.isChecked = false
                    reminderOptionsLayout.visibility = View.GONE
                } else {
                    reminderOptionsLayout.visibility = View.VISIBLE
                }
            } else {
                reminderOptionsLayout.visibility = View.GONE
            }
        }
    }

    private fun loadExistingTask() {
        viewModel.getTaskById(taskId) { task ->
            runOnUiThread {
                if (task != null) {
                    existingTask = task
                    populateTaskDetails(task)
                } else {
                    Toast.makeText(this, "Task not found", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }
    }

    private fun populateTaskDetails(task: Task) {
        etTitle.setText(task.title)
        etDescription.setText(task.description)

        when (task.priority) {
            Priority.HIGH -> toggleGroupPriority.check(R.id.btnPriorityHigh)
            Priority.MEDIUM -> toggleGroupPriority.check(R.id.btnPriorityMedium)
            Priority.LOW -> toggleGroupPriority.check(R.id.btnPriorityLow)
        }

        val dateMillis = task.dueDateTime ?: task.dueDate?.let { com.brainy.taskmanager.util.DateUtils.parseDbDate(it)?.time }
        dateMillis?.let { millis ->
            val calendar = Calendar.getInstance().apply { timeInMillis = millis }
            selectedYear = calendar.get(Calendar.YEAR)
            selectedMonth = calendar.get(Calendar.MONTH)
            selectedDay = calendar.get(Calendar.DAY_OF_MONTH)
            if (task.dueDateTime != null) {
                selectedHour = calendar.get(Calendar.HOUR_OF_DAY)
                selectedMinute = calendar.get(Calendar.MINUTE)
            } else {
                selectedHour = -1
                selectedMinute = -1
            }

            updateDateDisplay()
            updateTimeDisplay()
            btnClearDate.visibility = View.VISIBLE
        } ?: run {
            clearDateTime()
        }

        val reminderIndex = reminderOptions.indexOfFirst { it.second == task.reminderLeadMinutes }
        if (reminderIndex != -1) {
            spinnerReminderTime.setSelection(reminderIndex)
        } else {
            spinnerReminderTime.setSelection(1)
        }

        switchReminder.isChecked = task.reminderEnabled
        reminderOptionsLayout.visibility = if (task.reminderEnabled && task.dueDateTime != null) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

    private fun setupSaveButton() {
        etTitle.doAfterTextChanged {
            if (!it.isNullOrBlank()) {
                tilTitle.error = null
            }
        }

        btnSave.setOnClickListener {
            saveTask()
        }
    }

    private fun saveTask() {
        if (isSaving) return

        val title = etTitle.text?.toString()?.trim().orEmpty()
        if (title.isEmpty()) {
            tilTitle.error = getString(R.string.task_title_required)
            etTitle.requestFocus()
            return
        }
        tilTitle.error = null

        if (isEditMode && existingTask == null) {
            return
        }

        val description = etDescription.text?.toString()?.trim().orEmpty()
        val dueDateTime = getDueDateTime()
        val dueDate = dueDateTime?.let { com.brainy.taskmanager.util.DateUtils.formatToDbDate(it) }

        val priority = when (toggleGroupPriority.checkedButtonId) {
            R.id.btnPriorityHigh -> Priority.HIGH
            R.id.btnPriorityMedium -> Priority.MEDIUM
            else -> Priority.LOW
        }

        val reminderEnabled = switchReminder.isChecked && dueDateTime != null
        val selectedOptionIndex = spinnerReminderTime.selectedItemPosition
        val reminderLeadMinutes = if (selectedOptionIndex in reminderOptions.indices) {
            reminderOptions[selectedOptionIndex].second
        } else {
            10
        }

        // Show warning toast if reminder time is in the past
        if (reminderEnabled && dueDateTime != null) {
            val reminderTime = dueDateTime - (reminderLeadMinutes * 60 * 1000L)
            if (reminderTime <= System.currentTimeMillis()) {
                Toast.makeText(this, R.string.reminder_past_warning, Toast.LENGTH_LONG).show()
            }
        }

        isSaving = true
        btnSave.isEnabled = false

        if (isEditMode && existingTask != null) {
            val updatedTask = existingTask!!.copy(
                title = title,
                description = description,
                dueDate = dueDate,
                dueDateTime = dueDateTime,
                priority = priority,
                reminderEnabled = reminderEnabled,
                reminderLeadMinutes = reminderLeadMinutes
            )
            viewModel.updateTask(updatedTask)
            if (reminderEnabled) {
                reminderScheduler.scheduleReminder(updatedTask)
            } else {
                reminderScheduler.cancelReminder(updatedTask.id)
            }
            Toast.makeText(this, R.string.task_saved, Toast.LENGTH_SHORT).show()
            setResult(RESULT_OK)
            finish()
        } else {
            val newTask = Task(
                title = title,
                description = description,
                dueDate = dueDate,
                dueDateTime = dueDateTime,
                priority = priority,
                isCompleted = false,
                reminderEnabled = reminderEnabled,
                reminderLeadMinutes = reminderLeadMinutes
            )
            viewModel.insertTask(newTask) { generatedId ->
                val taskWithId = newTask.copy(id = generatedId.toInt())
                if (reminderEnabled) {
                    reminderScheduler.scheduleReminder(taskWithId)
                }
                runOnUiThread {
                    Toast.makeText(this, R.string.task_saved, Toast.LENGTH_SHORT).show()
                    setResult(RESULT_OK)
                    finish()
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        const val EXTRA_TASK_ID = "task_id"
    }
}
