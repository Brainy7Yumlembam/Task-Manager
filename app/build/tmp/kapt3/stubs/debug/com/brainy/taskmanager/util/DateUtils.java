package com.brainy.taskmanager.util;

/**
 * Utility for formatting and parsing dates across the application.
 *
 * Rules:
 * - UI date format: dd-MMM-yyyy (e.g., 15-Oct-2026)
 * - Database storage date format: yyyy-MM-dd (e.g., 2026-10-15)
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\t\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\u0004J!\u0010\t\u001a\u0004\u0018\u00010\u00042\b\u0010\n\u001a\u0004\u0018\u00010\u000b2\b\u0010\f\u001a\u0004\u0018\u00010\u0004\u00a2\u0006\u0002\u0010\rJ\u000e\u0010\u000e\u001a\u00020\u00042\u0006\u0010\u000f\u001a\u00020\u000bJ\u000e\u0010\u0010\u001a\u00020\u00042\u0006\u0010\u000f\u001a\u00020\u000bJ\u000e\u0010\u0011\u001a\u00020\u00042\u0006\u0010\u000f\u001a\u00020\u000bJ\u0010\u0010\u0012\u001a\u0004\u0018\u00010\u00132\u0006\u0010\u0014\u001a\u00020\u0004J\u0010\u0010\u0015\u001a\u0004\u0018\u00010\u00132\u0006\u0010\u0014\u001a\u00020\u0004R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0016"}, d2 = {"Lcom/brainy/taskmanager/util/DateUtils;", "", "()V", "DB_DATE_PATTERN", "", "UI_DATE_PATTERN", "UI_DATE_TIME_PATTERN", "formatDbDateToUiDate", "dbDate", "formatTaskDueDisplay", "dueDateTime", "", "dueDate", "(Ljava/lang/Long;Ljava/lang/String;)Ljava/lang/String;", "formatToDbDate", "timestamp", "formatToUiDate", "formatToUiDateTime", "parseDbDate", "Ljava/util/Date;", "dateString", "parseUiDate", "app_debug"})
public final class DateUtils {
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String DB_DATE_PATTERN = "yyyy-MM-dd";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String UI_DATE_PATTERN = "dd-MMM-yyyy";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String UI_DATE_TIME_PATTERN = "dd-MMM-yyyy, hh:mm a";
    @org.jetbrains.annotations.NotNull()
    public static final com.brainy.taskmanager.util.DateUtils INSTANCE = null;
    
    private DateUtils() {
        super();
    }
    
    /**
     * Formats a timestamp into the database storage format: yyyy-MM-dd.
     */
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String formatToDbDate(long timestamp) {
        return null;
    }
    
    /**
     * Formats a timestamp into the UI display format: dd-MMM-yyyy.
     */
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String formatToUiDate(long timestamp) {
        return null;
    }
    
    /**
     * Formats a timestamp into the UI display format with time: dd-MMM-yyyy, hh:mm a.
     */
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String formatToUiDateTime(long timestamp) {
        return null;
    }
    
    /**
     * Parses a database date string (yyyy-MM-dd) into a Date object.
     */
    @org.jetbrains.annotations.Nullable()
    public final java.util.Date parseDbDate(@org.jetbrains.annotations.NotNull()
    java.lang.String dateString) {
        return null;
    }
    
    /**
     * Parses a UI date string (dd-MMM-yyyy) into a Date object.
     */
    @org.jetbrains.annotations.Nullable()
    public final java.util.Date parseUiDate(@org.jetbrains.annotations.NotNull()
    java.lang.String dateString) {
        return null;
    }
    
    /**
     * Converts a database date string (yyyy-MM-dd) directly into a UI date string (dd-MMM-yyyy).
     */
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String formatDbDateToUiDate(@org.jetbrains.annotations.NotNull()
    java.lang.String dbDate) {
        return null;
    }
    
    /**
     * Formats due date & time for task list item display using the dd-MMM-yyyy standard.
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String formatTaskDueDisplay(@org.jetbrains.annotations.Nullable()
    java.lang.Long dueDateTime, @org.jetbrains.annotations.Nullable()
    java.lang.String dueDate) {
        return null;
    }
}