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

data class MortalityTimeConsumedPercentage(
    val years: Double,
    val months: Double,
    val days: Double,
    val hours: Double,
    val minutes: Double,
    val seconds: Double,
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

fun MortalityTime.calculateDeathPercentages(
    lifeExpectancy: Int,
): MortalityTimeConsumedPercentage {
    val years = 100 - years.toDouble().div(lifeExpectancy) * 100
    val months = 100 - months.toDouble().div(12) * 100
    val days = 100 - days.toDouble().div(365) * 100
    val hours = 100 - hours.toDouble().div(24) * 100
    val minutes = 100 - minutes.toDouble().div(60) * 100
    val seconds = 100 - seconds.toDouble().div(60) * 100

    return MortalityTimeConsumedPercentage(
        years = years,
        months = months,
        days = days,
        hours = hours,
        minutes = minutes,
        seconds = seconds
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

fun getHoursAndMinutesFromDaySeconds(daySeconds: Int): Pair<Int, Int> {
    val hours = (daySeconds / 3600)
    val minutes = daySeconds % 3600
    return Pair(hours, minutes)
}

fun getDaySecondsFromHourMinutes(hour: Int, minutes: Int): Int {
    val totalSeconds = (hour * 3600) + (minutes * 60)
    return if (totalSeconds <= 86440) {
        totalSeconds
    } else -1
}

fun Int.formatMinutes(): String {
    return if (this in 0..9) "0$this" else this.toString()
}