package com.app.weeklycalendar

import java.text.SimpleDateFormat
import java.util.*

data class CalendarDateModel(private val date: Date, var isSelected: Boolean = true) {

    private val dayFormat = SimpleDateFormat("EE", Locale.getDefault())
    private val yearFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())

    val calendarDay: String
        get() = dayFormat.format(date)

    val calendarYear: String
        get() = yearFormat.format(date)

    val calendarDate: String
        get() = Calendar.getInstance().apply { time = date }[Calendar.DAY_OF_MONTH].toString()
}
