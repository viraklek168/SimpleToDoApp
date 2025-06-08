package com.virak.simpletodoapp.utils

import android.icu.util.Calendar
import com.virak.simpletodoapp.R
import com.virak.simpletodoapp.utils.extensions.string

fun getMonthName(monthIndex:Int):String = when(monthIndex){
    Calendar.JANUARY -> R.string.month_january.string()
    Calendar.FEBRUARY -> R.string.month_february.string()
    Calendar.MARCH -> R.string.month_march.string()
    Calendar.APRIL -> R.string.month_april.string()
    Calendar.MAY -> R.string.month_may.string()
    Calendar.JUNE -> R.string.month_june.string()
    Calendar.JULY -> R.string.month_july.string()
    Calendar.AUGUST -> R.string.month_august.string()
    Calendar.SEPTEMBER -> R.string.month_september.string()
    Calendar.OCTOBER -> R.string.month_october.string()
    Calendar.NOVEMBER -> R.string.month_november.string()
    Calendar.DECEMBER -> R.string.month_december.string()
    else -> R.string.month_january.string() // default or fallback
}