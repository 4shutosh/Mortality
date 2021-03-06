package com.planner.mortality.utils

import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.periodUntil
import kotlinx.datetime.plus
import java.text.SimpleDateFormat
import java.util.*

data class MortalityTime(
    val years: Int,
    val months: Int,
    val days: Int,
    val hours: Int,
    val minutes: Int,
    val seconds: Int,
)

private val systemTimeZone = TimeZone.currentSystemDefault()


fun getTimeDifferenceInYears(timeStampMilliSeconds: Long): Int {
    val timeInstant = Instant.fromEpochMilliseconds(timeStampMilliSeconds)
    val currentInstant = Instant.fromEpochMilliseconds(Clock.System.now().toEpochMilliseconds())
    return timeInstant.periodUntil(currentInstant, systemTimeZone).years
}

fun getMortalityTimeDifference(timeStampMilliSeconds: Long): MortalityTime {
    // primary assumption here is that : given time is in future
    val timeInstant = Instant.fromEpochMilliseconds(timeStampMilliSeconds)
    val currentInstant = Instant.fromEpochMilliseconds(Clock.System.now().toEpochMilliseconds())

    val differenceDateTime =
        currentInstant.periodUntil(timeInstant, systemTimeZone)

    return MortalityTime(
        years = differenceDateTime.years,
        months = differenceDateTime.months,
        days = differenceDateTime.days,
        hours = differenceDateTime.hours,
        minutes = differenceDateTime.minutes,
        seconds = differenceDateTime.seconds
    )
}

fun addYearsToTimeStamp(timeStampMilliSeconds: Long, years: Int): Long {
    val timeInstant = Instant.fromEpochMilliseconds(timeStampMilliSeconds)
    val addedTimeStamp = timeInstant.plus(years, DateTimeUnit.YEAR, systemTimeZone)
    return addedTimeStamp.toEpochMilliseconds()
}

fun getFormattedDate(timeStampMilliSeconds: Long, format: String = "EEE, MMM d, ''yy"): String {
    return SimpleDateFormat(format, Locale.ENGLISH).format(Date(timeStampMilliSeconds))
}