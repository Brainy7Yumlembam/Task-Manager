package com.brainy.taskmanager

import com.brainy.taskmanager.util.DateUtils
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Test
import java.util.Calendar
import java.util.TimeZone

/**
 * Unit tests verifying UI (dd-MMM-yyyy) and database (yyyy-MM-dd) date formatting and parsing.
 */
class DateUtilsTest {

    @Test
    fun formatToDbDate_producesCorrectIsoFormat() {
        val calendar = Calendar.getInstance().apply {
            set(2026, Calendar.OCTOBER, 15, 14, 30, 0)
            set(Calendar.MILLISECOND, 0)
        }
        val formatted = DateUtils.formatToDbDate(calendar.timeInMillis)
        assertEquals("2026-10-15", formatted)
    }

    @Test
    fun formatToUiDate_producesCorrectDdMmmYyyyFormat() {
        val calendar = Calendar.getInstance().apply {
            set(2026, Calendar.OCTOBER, 15, 14, 30, 0)
            set(Calendar.MILLISECOND, 0)
        }
        val formatted = DateUtils.formatToUiDate(calendar.timeInMillis)
        assertEquals("15-Oct-2026", formatted)
    }

    @Test
    fun formatDbDateToUiDate_convertsDirectly() {
        val dbDate = "2026-10-15"
        val uiDate = DateUtils.formatDbDateToUiDate(dbDate)
        assertEquals("15-Oct-2026", uiDate)
    }

    @Test
    fun parseDbDate_validAndInvalid() {
        val validDate = DateUtils.parseDbDate("2026-10-15")
        assertNotNull(validDate)

        val invalidDate = DateUtils.parseDbDate("invalid-date")
        assertNull(invalidDate)
    }

    @Test
    fun parseUiDate_validAndInvalid() {
        val validDate = DateUtils.parseUiDate("15-Oct-2026")
        assertNotNull(validDate)

        val invalidDate = DateUtils.parseUiDate("invalid-date")
        assertNull(invalidDate)
    }

    @Test
    fun formatTaskDueDisplay_withOnlyDate() {
        val formatted = DateUtils.formatTaskDueDisplay(null, "2026-10-15")
        assertEquals("15-Oct-2026", formatted)
    }
}
