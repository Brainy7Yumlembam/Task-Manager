package com.brainy.taskmanager.ui;

/**
 * ViewModel for managing task data, filters, statistics, and reminder scheduling.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000h\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u000e\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020\u001bJ\u000e\u0010$\u001a\u00020\"2\u0006\u0010%\u001a\u00020\fJ\u0006\u0010&\u001a\u00020\tJ$\u0010\'\u001a\u00020\"2\u0006\u0010%\u001a\u00020\f2\u0014\u0010(\u001a\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u001b\u0012\u0004\u0012\u00020\"0)J$\u0010*\u001a\u00020\"2\u0006\u0010#\u001a\u00020\u001b2\u0014\b\u0002\u0010+\u001a\u000e\u0012\u0004\u0012\u00020,\u0012\u0004\u0012\u00020\"0)J\u001a\u0010-\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001b0\u001a0\u000b2\u0006\u0010.\u001a\u00020/J\u000e\u00100\u001a\u00020\"2\u0006\u0010\u000f\u001a\u00020\tJ\u000e\u00101\u001a\u00020\"2\u0006\u0010#\u001a\u00020\u001bJ\u000e\u00102\u001a\u00020\"2\u0006\u0010#\u001a\u00020\u001bR\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0017\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\t0\u0010\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0017\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\f0\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u000eR\u0017\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\f0\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u000eR\u000e\u0010\u0017\u001a\u00020\u0018X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R#\u0010\u0019\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001b0\u001a0\u000b\u00a2\u0006\u000e\n\u0000\u0012\u0004\b\u001c\u0010\u001d\u001a\u0004\b\u001e\u0010\u000eR\u0017\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\f0\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010\u000e\u00a8\u00063"}, d2 = {"Lcom/brainy/taskmanager/ui/TaskViewModel;", "Landroidx/lifecycle/AndroidViewModel;", "repository", "Lcom/brainy/taskmanager/data/TaskRepository;", "application", "Landroid/app/Application;", "(Lcom/brainy/taskmanager/data/TaskRepository;Landroid/app/Application;)V", "_filterType", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/brainy/taskmanager/ui/FilterType;", "completedCount", "Landroidx/lifecycle/LiveData;", "", "getCompletedCount", "()Landroidx/lifecycle/LiveData;", "filterType", "Lkotlinx/coroutines/flow/StateFlow;", "getFilterType", "()Lkotlinx/coroutines/flow/StateFlow;", "overdueCount", "getOverdueCount", "pendingCount", "getPendingCount", "reminderScheduler", "Lcom/brainy/taskmanager/notification/TaskReminderScheduler;", "tasks", "", "Lcom/brainy/taskmanager/data/Task;", "getTasks$annotations", "()V", "getTasks", "totalCount", "getTotalCount", "deleteTask", "", "task", "deleteTaskById", "taskId", "getCurrentFilter", "getTaskById", "callback", "Lkotlin/Function1;", "insertTask", "onResult", "", "searchTasks", "query", "", "setFilter", "toggleTaskCompletion", "updateTask", "app_debug"})
public final class TaskViewModel extends androidx.lifecycle.AndroidViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.brainy.taskmanager.data.TaskRepository repository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.brainy.taskmanager.notification.TaskReminderScheduler reminderScheduler = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.brainy.taskmanager.ui.FilterType> _filterType = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.brainy.taskmanager.ui.FilterType> filterType = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.LiveData<java.util.List<com.brainy.taskmanager.data.Task>> tasks = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.LiveData<java.lang.Integer> totalCount = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.LiveData<java.lang.Integer> pendingCount = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.LiveData<java.lang.Integer> completedCount = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.LiveData<java.lang.Integer> overdueCount = null;
    
    public TaskViewModel(@org.jetbrains.annotations.NotNull()
    com.brainy.taskmanager.data.TaskRepository repository, @org.jetbrains.annotations.NotNull()
    android.app.Application application) {
        super(null);
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.brainy.taskmanager.ui.FilterType> getFilterType() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.util.List<com.brainy.taskmanager.data.Task>> getTasks() {
        return null;
    }
    
    @kotlin.OptIn(markerClass = {kotlinx.coroutines.ExperimentalCoroutinesApi.class})
    @java.lang.Deprecated()
    public static void getTasks$annotations() {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.lang.Integer> getTotalCount() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.lang.Integer> getPendingCount() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.lang.Integer> getCompletedCount() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.lang.Integer> getOverdueCount() {
        return null;
    }
    
    /**
     * Updates the current task filter (ALL, PENDING, COMPLETED).
     */
    public final void setFilter(@org.jetbrains.annotations.NotNull()
    com.brainy.taskmanager.ui.FilterType filterType) {
    }
    
    /**
     * Gets the currently selected filter type.
     */
    @org.jetbrains.annotations.NotNull()
    public final com.brainy.taskmanager.ui.FilterType getCurrentFilter() {
        return null;
    }
    
    /**
     * Searches tasks by title query.
     */
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.util.List<com.brainy.taskmanager.data.Task>> searchTasks(@org.jetbrains.annotations.NotNull()
    java.lang.String query) {
        return null;
    }
    
    /**
     * Inserts a new task and triggers reminder scheduling if reminder is enabled.
     * Calls [onResult] with the newly generated task ID.
     */
    public final void insertTask(@org.jetbrains.annotations.NotNull()
    com.brainy.taskmanager.data.Task task, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.Long, kotlin.Unit> onResult) {
    }
    
    /**
     * Updates an existing task and updates reminder scheduling accordingly.
     */
    public final void updateTask(@org.jetbrains.annotations.NotNull()
    com.brainy.taskmanager.data.Task task) {
    }
    
    /**
     * Deletes a task and cancels any associated reminder.
     */
    public final void deleteTask(@org.jetbrains.annotations.NotNull()
    com.brainy.taskmanager.data.Task task) {
    }
    
    /**
     * Deletes a task by its ID and cancels any associated reminder.
     */
    public final void deleteTaskById(int taskId) {
    }
    
    /**
     * Toggles the completion status of a task.
     * - If marked complete: cancels any pending reminder.
     * - If marked incomplete: re-schedules the reminder if enabled and due date is in the future.
     */
    public final void toggleTaskCompletion(@org.jetbrains.annotations.NotNull()
    com.brainy.taskmanager.data.Task task) {
    }
    
    /**
     * Retrieves a task by its ID asynchronously.
     */
    public final void getTaskById(int taskId, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.brainy.taskmanager.data.Task, kotlin.Unit> callback) {
    }
}