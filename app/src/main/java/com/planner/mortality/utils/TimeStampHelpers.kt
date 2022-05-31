package com.planner.mortality.utils

import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs


fun getTimeDifferenceInYears(timestamp: Long): Int {
    val today = Calendar.getInstance()
    val timeStampCalendar = Calendar.getInstance()
    timeStampCalendar.timeInMillis = timestamp

    var differenceYearTimeStamp =
        abs(today.get(Calendar.YEAR) - timeStampCalendar.get(Calendar.YEAR))
    if (today.get(Calendar.YEAR) > timeStampCalendar.get(Calendar.YEAR)) {
        differenceYearTimeStamp--
    }
    return differenceYearTimeStamp
}

fun getFormattedDate(timeStampMilliSeconds: Long, format: String = "EEE, MMM d, ''yy"): String {
    return SimpleDateFormat(format, Locale.ENGLISH).format(Date(timeStampMilliSeconds))
}