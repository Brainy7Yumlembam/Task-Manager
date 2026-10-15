package com.brainy.taskmanager.data

import androidx.room.TypeConverter

/**
 * Type converters to allow Room to store and retrieve custom types.
 */
class Converters {

    @TypeConverter
    fun fromPriority(priority: Priority?): String? {
        return priority?.name
    }

    @TypeConverter
    fun toPriority(value: String?): Priority? {
        return value?.let {
            try {
                Priority.valueOf(it)
            } catch (e: IllegalArgumentException) {
                Priority.MEDIUM
            }
        }
    }

    @TypeConverter
    fun fromDate(date: java.util.Date?): String? {
        return date?.let { com.brainy.taskmanager.util.DateUtils.formatToDbDate(it.time) }
    }

    @TypeConverter
    fun toDate(dateString: String?): java.util.Date? {
        return dateString?.let { com.brainy.taskmanager.util.DateUtils.parseDbDate(it) }
    }
}
