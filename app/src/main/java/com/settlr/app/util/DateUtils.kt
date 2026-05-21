package com.settlr.app.util

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

fun formatTimestamp(timestamp: Long): String {
    val date = Calendar.getInstance().apply { timeInMillis = timestamp }
    val today = Calendar.getInstance()
    val yesterday = Calendar.getInstance().apply { add(Calendar.DAY_OF_YEAR, -1) }

    val diffInMillis = today.timeInMillis - date.timeInMillis
    val diffInDays = diffInMillis / (1000 * 60 * 60 * 24)

    return when {
        isSameDay(date, today) -> "Today"
        isSameDay(date, yesterday) -> "Yesterday"
        diffInDays < 7 -> SimpleDateFormat("EEEE", Locale.getDefault()).format(date.time)
        else -> SimpleDateFormat("d MMM", Locale.getDefault()).format(date.time)
    }
}

fun formatFullDate(timestamp: Long): String {
    val date = Calendar.getInstance().apply { timeInMillis = timestamp }
    return SimpleDateFormat("d MMM yyyy", Locale.getDefault()).format(date.time)
}

private fun isSameDay(cal1: Calendar, cal2: Calendar): Boolean {
    return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
           cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)
}
