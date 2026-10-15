package com.brainy.taskmanager.data;

/**
 * Data Access Object for the `tasks` table.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\u0004\n\u0002\u0010\t\n\u0002\b\t\n\u0002\u0010\u000e\n\u0002\b\u0002\bg\u0018\u00002\u00020\u0001J\u0016\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u0016\u0010\u0007\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\tH\u00a7@\u00a2\u0006\u0002\u0010\nJ\u0014\u0010\u000b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\r0\fH\'J\u000e\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\t0\fH\'J\u0014\u0010\u000f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\r0\fH\'J\u0016\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\t0\f2\u0006\u0010\u0011\u001a\u00020\u0012H\'J\u000e\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\t0\fH\'J\u0014\u0010\u0014\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\r0\fH\'J\u0018\u0010\u0015\u001a\u0004\u0018\u00010\u00052\u0006\u0010\b\u001a\u00020\tH\u00a7@\u00a2\u0006\u0002\u0010\nJ\u001c\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00050\r2\u0006\u0010\u0011\u001a\u00020\u0012H\u00a7@\u00a2\u0006\u0002\u0010\u0017J\u000e\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\t0\fH\'J\u0016\u0010\u0019\u001a\u00020\u00122\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u001c\u0010\u001a\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\r0\f2\u0006\u0010\u001b\u001a\u00020\u001cH\'J\u0016\u0010\u001d\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006\u00a8\u0006\u001e"}, d2 = {"Lcom/brainy/taskmanager/data/TaskDao;", "", "deleteTask", "", "task", "Lcom/brainy/taskmanager/data/Task;", "(Lcom/brainy/taskmanager/data/Task;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteTaskById", "taskId", "", "(ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAllTasks", "Lkotlinx/coroutines/flow/Flow;", "", "getCompletedCount", "getCompletedTasks", "getOverdueCount", "currentTime", "", "getPendingCount", "getPendingTasks", "getTaskById", "getTasksWithPendingReminders", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getTotalCount", "insertTask", "searchTasks", "query", "", "updateTask", "app_debug"})
@androidx.room.Dao()
public abstract interface TaskDao {
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertTask(@org.jetbrains.annotations.NotNull()
    com.brainy.taskmanager.data.Task task, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion);
    
    @androidx.room.Update()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateTask(@org.jetbrains.annotations.NotNull()
    com.brainy.taskmanager.data.Task task, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Delete()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteTask(@org.jetbrains.annotations.NotNull()
    com.brainy.taskmanager.data.Task task, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "DELETE FROM tasks WHERE id = :taskId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteTaskById(int taskId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM tasks WHERE id = :taskId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getTaskById(int taskId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.brainy.taskmanager.data.Task> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM tasks ORDER BY isCompleted ASC, CASE WHEN dueDateTime IS NULL THEN 1 ELSE 0 END, dueDateTime ASC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.brainy.taskmanager.data.Task>> getAllTasks();
    
    @androidx.room.Query(value = "SELECT * FROM tasks WHERE isCompleted = 0 ORDER BY CASE WHEN dueDateTime IS NULL THEN 1 ELSE 0 END, dueDateTime ASC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.brainy.taskmanager.data.Task>> getPendingTasks();
    
    @androidx.room.Query(value = "SELECT * FROM tasks WHERE isCompleted = 1 ORDER BY dueDateTime DESC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.brainy.taskmanager.data.Task>> getCompletedTasks();
    
    @androidx.room.Query(value = "SELECT * FROM tasks WHERE title LIKE \'%\' || :query || \'%\' ORDER BY isCompleted ASC, CASE WHEN dueDateTime IS NULL THEN 1 ELSE 0 END, dueDateTime ASC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.brainy.taskmanager.data.Task>> searchTasks(@org.jetbrains.annotations.NotNull()
    java.lang.String query);
    
    @androidx.room.Query(value = "SELECT COUNT(*) FROM tasks")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.lang.Integer> getTotalCount();
    
    @androidx.room.Query(value = "SELECT COUNT(*) FROM tasks WHERE isCompleted = 0")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.lang.Integer> getPendingCount();
    
    @androidx.room.Query(value = "SELECT COUNT(*) FROM tasks WHERE isCompleted = 1")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.lang.Integer> getCompletedCount();
    
    @androidx.room.Query(value = "SELECT COUNT(*) FROM tasks WHERE isCompleted = 0 AND dueDateTime IS NOT NULL AND dueDateTime < :currentTime")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.lang.Integer> getOverdueCount(long currentTime);
    
    @androidx.room.Query(value = "SELECT * FROM tasks WHERE isCompleted = 0 AND reminderEnabled = 1 AND dueDateTime IS NOT NULL AND dueDateTime > :currentTime")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getTasksWithPendingReminders(long currentTime, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.brainy.taskmanager.data.Task>> $completion);
}