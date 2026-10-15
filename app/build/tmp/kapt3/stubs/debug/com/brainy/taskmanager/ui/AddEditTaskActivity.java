package com.brainy.taskmanager.ui;

/**
 * Activity for creating a new task or editing an existing task.
 * Supports setting title, description, due date/time, priority level, and reminders.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0096\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000f\u0018\u0000 L2\u00020\u0001:\u0001LB\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u00101\u001a\u000202H\u0002J\u000f\u00103\u001a\u0004\u0018\u000104H\u0002\u00a2\u0006\u0002\u00105J\b\u00106\u001a\u000202H\u0002J\b\u00107\u001a\u000202H\u0002J\b\u00108\u001a\u000202H\u0002J\u0012\u00109\u001a\u0002022\b\u0010:\u001a\u0004\u0018\u00010;H\u0014J\u0010\u0010<\u001a\u00020\u00112\u0006\u0010=\u001a\u00020>H\u0016J\b\u0010?\u001a\u000202H\u0002J\u0010\u0010@\u001a\u0002022\u0006\u0010A\u001a\u00020\u000fH\u0002J\b\u0010B\u001a\u000202H\u0002J\b\u0010C\u001a\u000202H\u0002J\b\u0010D\u001a\u000202H\u0002J\b\u0010E\u001a\u000202H\u0002J\b\u0010F\u001a\u000202H\u0002J\b\u0010G\u001a\u000202H\u0002J\b\u0010H\u001a\u000202H\u0002J\b\u0010I\u001a\u000202H\u0002J\b\u0010J\u001a\u000202H\u0002J\b\u0010K\u001a\u000202H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\nX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\nX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\nX\u0082.\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u000e\u001a\u0004\u0018\u00010\u000fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0011X\u0082\u000e\u00a2\u0006\u0002\n\u0000R \u0010\u0013\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0016\u0012\u0004\u0012\u00020\u00170\u00150\u0014X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u001aX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u0017X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u0017X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u0017X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\u0017X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001f\u001a\u00020\u0017X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010 \u001a\u00020!X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\"\u001a\u00020#X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010$\u001a\u00020\u0017X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010%\u001a\u00020&X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\'\u001a\u00020&X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010(\u001a\u00020&X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010)\u001a\u00020&X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010*\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010+\u001a\u00020,X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010-\u001a\u00020.X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010/\u001a\u000200X\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006M"}, d2 = {"Lcom/brainy/taskmanager/ui/AddEditTaskActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "btnClearDate", "Landroid/view/View;", "btnSave", "Landroid/widget/Button;", "dateFormat", "Ljava/text/SimpleDateFormat;", "etDescription", "Lcom/google/android/material/textfield/TextInputEditText;", "etDueDate", "etDueTime", "etTitle", "existingTask", "Lcom/brainy/taskmanager/data/Task;", "isEditMode", "", "isSaving", "reminderOptions", "", "Lkotlin/Pair;", "", "", "reminderOptionsLayout", "reminderScheduler", "Lcom/brainy/taskmanager/notification/TaskReminderScheduler;", "selectedDay", "selectedHour", "selectedMinute", "selectedMonth", "selectedYear", "spinnerReminderTime", "Landroid/widget/Spinner;", "switchReminder", "Landroid/widget/CompoundButton;", "taskId", "tilDescription", "Lcom/google/android/material/textfield/TextInputLayout;", "tilDueDate", "tilDueTime", "tilTitle", "timeFormat", "toggleGroupPriority", "Lcom/google/android/material/button/MaterialButtonToggleGroup;", "toolbar", "Landroidx/appcompat/widget/Toolbar;", "viewModel", "Lcom/brainy/taskmanager/ui/TaskViewModel;", "clearDateTime", "", "getDueDateTime", "", "()Ljava/lang/Long;", "initDependencies", "initViews", "loadExistingTask", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onOptionsItemSelected", "item", "Landroid/view/MenuItem;", "parseIntentExtras", "populateTaskDetails", "task", "saveTask", "setupDateTimePickers", "setupPriorityToggle", "setupReminderSection", "setupSaveButton", "setupToolbar", "showDatePicker", "showTimePicker", "updateDateDisplay", "updateTimeDisplay", "Companion", "app_debug"})
public final class AddEditTaskActivity extends androidx.appcompat.app.AppCompatActivity {
    private com.brainy.taskmanager.ui.TaskViewModel viewModel;
    private com.brainy.taskmanager.notification.TaskReminderScheduler reminderScheduler;
    private androidx.appcompat.widget.Toolbar toolbar;
    private com.google.android.material.textfield.TextInputLayout tilTitle;
    private com.google.android.material.textfield.TextInputEditText etTitle;
    private com.google.android.material.textfield.TextInputLayout tilDescription;
    private com.google.android.material.textfield.TextInputEditText etDescription;
    private com.google.android.material.textfield.TextInputLayout tilDueDate;
    private com.google.android.material.textfield.TextInputEditText etDueDate;
    private com.google.android.material.textfield.TextInputLayout tilDueTime;
    private com.google.android.material.textfield.TextInputEditText etDueTime;
    private android.view.View btnClearDate;
    private com.google.android.material.button.MaterialButtonToggleGroup toggleGroupPriority;
    private android.widget.CompoundButton switchReminder;
    private android.view.View reminderOptionsLayout;
    private android.widget.Spinner spinnerReminderTime;
    private android.widget.Button btnSave;
    private int taskId = -1;
    private boolean isEditMode = false;
    @org.jetbrains.annotations.Nullable()
    private com.brainy.taskmanager.data.Task existingTask;
    private boolean isSaving = false;
    private int selectedYear = -1;
    private int selectedMonth = -1;
    private int selectedDay = -1;
    private int selectedHour = -1;
    private int selectedMinute = -1;
    @org.jetbrains.annotations.NotNull()
    private final java.text.SimpleDateFormat dateFormat = null;
    @org.jetbrains.annotations.NotNull()
    private final java.text.SimpleDateFormat timeFormat = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<kotlin.Pair<java.lang.String, java.lang.Integer>> reminderOptions = null;
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String EXTRA_TASK_ID = "task_id";
    @org.jetbrains.annotations.NotNull()
    public static final com.brainy.taskmanager.ui.AddEditTaskActivity.Companion Companion = null;
    
    public AddEditTaskActivity() {
        super();
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    private final void initDependencies() {
    }
    
    private final void initViews() {
    }
    
    private final void parseIntentExtras() {
    }
    
    private final void setupToolbar() {
    }
    
    private final void setupDateTimePickers() {
    }
    
    private final void showDatePicker() {
    }
    
    private final void showTimePicker() {
    }
    
    private final void updateDateDisplay() {
    }
    
    private final void updateTimeDisplay() {
    }
    
    private final void clearDateTime() {
    }
    
    private final java.lang.Long getDueDateTime() {
        return null;
    }
    
    private final void setupPriorityToggle() {
    }
    
    private final void setupReminderSection() {
    }
    
    private final void loadExistingTask() {
    }
    
    private final void populateTaskDetails(com.brainy.taskmanager.data.Task task) {
    }
    
    private final void setupSaveButton() {
    }
    
    private final void saveTask() {
    }
    
    @java.lang.Override()
    public boolean onOptionsItemSelected(@org.jetbrains.annotations.NotNull()
    android.view.MenuItem item) {
        return false;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0005"}, d2 = {"Lcom/brainy/taskmanager/ui/AddEditTaskActivity$Companion;", "", "()V", "EXTRA_TASK_ID", "", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}