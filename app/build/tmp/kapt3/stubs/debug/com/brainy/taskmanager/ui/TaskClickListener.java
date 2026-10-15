package com.brainy.taskmanager.ui;

/**
 * Listener interface for handling user interactions with a task item.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&J\u0010\u0010\u0006\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&J\u0018\u0010\u0007\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\tH&\u00a8\u0006\n"}, d2 = {"Lcom/brainy/taskmanager/ui/TaskClickListener;", "", "onDeleteClick", "", "task", "Lcom/brainy/taskmanager/data/Task;", "onEditClick", "onTaskCheckedChanged", "isChecked", "", "app_debug"})
public abstract interface TaskClickListener {
    
    public abstract void onTaskCheckedChanged(@org.jetbrains.annotations.NotNull()
    com.brainy.taskmanager.data.Task task, boolean isChecked);
    
    public abstract void onEditClick(@org.jetbrains.annotations.NotNull()
    com.brainy.taskmanager.data.Task task);
    
    public abstract void onDeleteClick(@org.jetbrains.annotations.NotNull()
    com.brainy.taskmanager.data.Task task);
}