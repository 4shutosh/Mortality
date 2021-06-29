package com.day.timer.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.day.timer.R

private val OpenSans = FontFamily(
    Font(R.font.font_open_sans_regular),
    Font(R.font.font_open_sans_semi_bold, FontWeight.SemiBold)
)

val MortalityTypography = Typography(
    defaultFontFamily = OpenSans
)