package com.tehronshoh.todolist.presentation.util

import java.util.Calendar

fun Calendar.getDateString(): String {
    val calendar = this
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)
    val hour = calendar.get(Calendar.HOUR_OF_DAY)
    val minute = calendar.get(Calendar.MINUTE)
    return "$day-${month + 1}-$year $hour:$minute"
}