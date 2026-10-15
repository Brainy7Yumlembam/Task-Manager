package com.brainy.taskmanager.ui;

/**
 * Main Activity of the Task Management application.
 * Displays dashboard summary counters, filterable task list, and provides access to task operations.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u008c\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0015\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\n\u0018\u0000 @2\u00020\u00012\u00020\u0002:\u0001@B\u0005\u00a2\u0006\u0002\u0010\u0003J\b\u0010!\u001a\u00020\"H\u0002J\b\u0010#\u001a\u00020\"H\u0002J\b\u0010$\u001a\u00020\"H\u0002J\u0012\u0010%\u001a\u00020\"2\b\u0010&\u001a\u0004\u0018\u00010\'H\u0014J\u0010\u0010(\u001a\u00020\"2\u0006\u0010)\u001a\u00020*H\u0016J\u0010\u0010+\u001a\u00020\"2\u0006\u0010)\u001a\u00020*H\u0016J-\u0010,\u001a\u00020\"2\u0006\u0010-\u001a\u00020.2\u000e\u0010/\u001a\n\u0012\u0006\b\u0001\u0012\u000201002\u0006\u00102\u001a\u000203H\u0016\u00a2\u0006\u0002\u00104J\u0018\u00105\u001a\u00020\"2\u0006\u0010)\u001a\u00020*2\u0006\u00106\u001a\u000207H\u0016J\b\u00108\u001a\u00020\"H\u0002J\b\u00109\u001a\u00020\"H\u0002J\b\u0010:\u001a\u00020\"H\u0002J\b\u0010;\u001a\u00020\"H\u0002J\b\u0010<\u001a\u00020\"H\u0002J\b\u0010=\u001a\u00020\"H\u0002J\u0010\u0010>\u001a\u00020\"2\u0006\u0010?\u001a\u00020\u000bH\u0002R\u001c\u0010\u0004\u001a\u0010\u0012\f\u0012\n \u0007*\u0004\u0018\u00010\u00060\u00060\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0015X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0017X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0017X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u0017X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u0017X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u0017X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u0017X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u0017X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\u0017X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001f\u001a\u00020 X\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006A"}, d2 = {"Lcom/brainy/taskmanager/ui/MainActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "Lcom/brainy/taskmanager/ui/TaskClickListener;", "()V", "addEditTaskLauncher", "Landroidx/activity/result/ActivityResultLauncher;", "Landroid/content/Intent;", "kotlin.jvm.PlatformType", "chipGroupFilter", "Lcom/google/android/material/chip/ChipGroup;", "currentFilter", "Lcom/brainy/taskmanager/ui/FilterType;", "emptyStateLayout", "Landroid/view/View;", "fabAddTask", "Lcom/google/android/material/floatingactionbutton/FloatingActionButton;", "recyclerViewTasks", "Landroidx/recyclerview/widget/RecyclerView;", "reminderScheduler", "Lcom/brainy/taskmanager/notification/TaskReminderScheduler;", "taskAdapter", "Lcom/brainy/taskmanager/ui/TaskAdapter;", "tvCompletedCount", "Landroid/widget/TextView;", "tvEmptyMessage", "tvEmptyTitle", "tvGreeting", "tvOverdueCount", "tvPendingCount", "tvTodayDate", "tvTotalCount", "viewModel", "Lcom/brainy/taskmanager/ui/TaskViewModel;", "initDependencies", "", "initViews", "observeViewModel", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onDeleteClick", "task", "Lcom/brainy/taskmanager/data/Task;", "onEditClick", "onRequestPermissionsResult", "requestCode", "", "permissions", "", "", "grantResults", "", "(I[Ljava/lang/String;[I)V", "onTaskCheckedChanged", "isChecked", "", "requestNotificationPermission", "setupFab", "setupFilterChips", "setupHeader", "setupRecyclerView", "setupToolbar", "updateEmptyStateText", "filterType", "Companion", "app_debug"})
public final class MainActivity extends androidx.appcompat.app.AppCompatActivity implements com.brainy.taskmanager.ui.TaskClickListener {
    private com.brainy.taskmanager.ui.TaskViewModel viewModel;
    private com.brainy.taskmanager.ui.TaskAdapter taskAdapter;
    private com.brainy.taskmanager.notification.TaskReminderScheduler reminderScheduler;
    private android.widget.TextView tvTotalCount;
    private android.widget.TextView tvPendingCount;
    private android.widget.TextView tvCompletedCount;
    private android.widget.TextView tvOverdueCount;
    private android.widget.TextView tvGreeting;
    private android.widget.TextView tvTodayDate;
    private com.google.android.material.chip.ChipGroup chipGroupFilter;
    private androidx.recyclerview.widget.RecyclerView recyclerViewTasks;
    private android.view.View emptyStateLayout;
    private android.widget.TextView tvEmptyTitle;
    private android.widget.TextView tvEmptyMessage;
    private com.google.android.material.floatingactionbutton.FloatingActionButton fabAddTask;
    @org.jetbrains.annotations.NotNull()
    private com.brainy.taskmanager.ui.FilterType currentFilter = com.brainy.taskmanager.ui.FilterType.ALL;
    @org.jetbrains.annotations.NotNull()
    private final androidx.activity.result.ActivityResultLauncher<android.content.Intent> addEditTaskLauncher = null;
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String EXTRA_TASK_ID = "task_id";
    private static final int NOTIFICATION_PERMISSION_REQUEST_CODE = 1001;
    @org.jetbrains.annotations.NotNull()
    public static final com.brainy.taskmanager.ui.MainActivity.Companion Companion = null;
    
    public MainActivity() {
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
    
    private final void setupHeader() {
    }
    
    private final void setupToolbar() {
    }
    
    private final void setupRecyclerView() {
    }
    
    private final void setupFilterChips() {
    }
    
    private final void setupFab() {
    }
    
    private final void observeViewModel() {
    }
    
    private final void updateEmptyStateText(com.brainy.taskmanager.ui.FilterType filterType) {
    }
    
    @java.lang.Override()
    public void onTaskCheckedChanged(@org.jetbrains.annotations.NotNull()
    com.brainy.taskmanager.data.Task task, boolean isChecked) {
    }
    
    @java.lang.Override()
    public void onEditClick(@org.jetbrains.annotations.NotNull()
    com.brainy.taskmanager.data.Task task) {
    }
    
    @java.lang.Override()
    public void onDeleteClick(@org.jetbrains.annotations.NotNull()
    com.brainy.taskmanager.data.Task task) {
    }
    
    private final void requestNotificationPermission() {
    }
    
    @java.lang.Override()
    public void onRequestPermissionsResult(int requestCode, @org.jetbrains.annotations.NotNull()
    java.lang.String[] permissions, @org.jetbrains.annotations.NotNull()
    int[] grantResults) {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0007"}, d2 = {"Lcom/brainy/taskmanager/ui/MainActivity$Companion;", "", "()V", "EXTRA_TASK_ID", "", "NOTIFICATION_PERMISSION_REQUEST_CODE", "", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}