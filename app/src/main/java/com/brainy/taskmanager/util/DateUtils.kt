package com.brainy.taskmanager.util

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

/**
 * Utility for formatting and parsing dates across the application.
 *
 * Rules:
 * - UI date format: dd-MMM-yyyy (e.g., 15-Oct-2026)
 * - Database storage date format: yyyy-MM-dd (e.g., 2026-10-15)
 */
object DateUtils {

    const val DB_DATE_PATTERN = "yyyy-MM-dd"
    const val UI_DATE_PATTERN = "dd-MMM-yyyy"
    const val UI_DATE_TIME_PATTERN = "dd-MMM-yyyy, hh:mm a"

    /**
     * Formats a timestamp into the database storage format: yyyy-MM-dd.
     */
    fun formatToDbDate(timestamp: Long): String {
        return SimpleDateFormat(DB_DATE_PATTERN, Locale.US).format(Date(timestamp))
    }

    /**
     * Formats a timestamp into the UI display format: dd-MMM-yyyy.
     */
    fun formatToUiDate(timestamp: Long): String {
        return SimpleDateFormat(UI_DATE_PATTERN, Locale.ENGLISH).format(Date(timestamp))
    }

    /**
     * Formats a timestamp into the UI display format with time: dd-MMM-yyyy, hh:mm a.
     */
    fun formatToUiDateTime(timestamp: Long): String {
        return SimpleDateFormat(UI_DATE_TIME_PATTERN, Locale.ENGLISH).format(Date(timestamp))
    }

    /**
     * Parses a database date string (yyyy-MM-dd) into a Date object.
     */
    fun parseDbDate(dateString: String): Date? {
        return try {
            SimpleDateFormat(DB_DATE_PATTERN, Locale.US).parse(dateString)
        } catch (e: Exception) {
            null
        }
    }

    /**
     * Parses a UI date string (dd-MMM-yyyy) into a Date object.
     */
    fun parseUiDate(dateString: String): Date? {
        return try {
            SimpleDateFormat(UI_DATE_PATTERN, Locale.ENGLISH).parse(dateString)
        } catch (e: Exception) {
            null
        }
    }

    /**
     * Converts a database date string (yyyy-MM-dd) directly into a UI date string (dd-MMM-yyyy).
     */
    fun formatDbDateToUiDate(dbDate: String): String {
        val date = parseDbDate(dbDate) ?: return dbDate
        return SimpleDateFormat(UI_DATE_PATTERN, Locale.ENGLISH).format(date)
    }

    /**
     * Formats due date & time for task list item display using the dd-MMM-yyyy standard.
     */
    fun formatTaskDueDisplay(dueDateTime: Long?, dueDate: String?): String? {
        if (dueDateTime != null) {
            val cal = Calendar.getInstance().apply { timeInMillis = dueDateTime }
            val hasTime = cal.get(Calendar.HOUR_OF_DAY) != 0 || cal.get(Calendar.MINUTE) != 0
            return if (hasTime) {
                SimpleDateFormat(UI_DATE_TIME_PATTERN, Locale.ENGLISH).format(cal.time)
            } else {
                SimpleDateFormat(UI_DATE_PATTERN, Locale.ENGLISH).format(cal.time)
            }
        }
        if (!dueDate.isNullOrBlank()) {
            return formatDbDateToUiDate(dueDate)
        }
        return null
    }
}
