package com.planner.mortality.utils.extensions

import android.view.View
import com.planner.mortality.databinding.LayoutMortalityTimerBinding
import com.planner.mortality.utils.MortalityTime

fun View?.visible() {
    if (this?.visibility != View.VISIBLE) this?.visibility = View.VISIBLE
}

fun View?.gone() {
    if (this?.visibility != View.GONE) this?.visibility = View.GONE
}


fun LayoutMortalityTimerBinding.bindTime(mortalityTime: MortalityTime) {
    val deathTimeYears = "${mortalityTime.years}\nYears"
    val deathTimeMonths = "${mortalityTime.months}\nMonths"
    val deathTimeDays = "${mortalityTime.days}\nDays"
    val deathTimeHours = "${mortalityTime.hours}\nDays"
    val deathTimeMinutes = "${mortalityTime.minutes}\nMinutes"
    val deathTimeSeconds = "${mortalityTime.seconds}\nSeconds"
    tvYears.text = deathTimeYears
    tvMonths.text = deathTimeMonths
    tvDays.text = deathTimeDays
    tvHours.text = deathTimeHours
    tvMinutes.text = deathTimeMinutes
    tvSeconds.text = deathTimeSeconds
}