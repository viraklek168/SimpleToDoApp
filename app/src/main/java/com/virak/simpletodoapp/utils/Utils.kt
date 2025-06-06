package com.virak.simpletodoapp.utils

import android.content.Context
import android.content.Intent
import android.icu.util.Calendar
import com.virak.simpletodoapp.Application
import com.virak.simpletodoapp.MyCalendarDay
import com.virak.simpletodoapp.MyCalendarDisplayDate
import com.virak.simpletodoapp.R

object ActivityManager {
    fun openActivity(currentActivity:Context,targetActivity:Class<*>){
        val intent = Intent(currentActivity,targetActivity)
        currentActivity.startActivity(intent)
    }
}

fun Int.string() = Application.baseApplicationContext.getString(this)

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

object CalendarManager{
    sealed class CalendarAction{
        object ClickNextMonth : CalendarAction()
        object ClickPreviousMonth : CalendarAction()
    }
    val maximumYear = 2100
    val defaultYear = 1970
    val defaultMonth = Calendar.JANUARY
    val defaultDay = 1
    private var startYear = 0
    private var currentYear = 0
    private var currentDay = 0
    private var currentMonth = 0
    private val calendarObject = Calendar.getInstance().apply {
        currentYear = get(Calendar.YEAR)
        currentMonth = get(Calendar.MONTH)
        currentDay = get(Calendar.DAY_OF_MONTH)
        clear()
        startYear = get(Calendar.YEAR)
        clear()
    }
    fun getDate(targetDay:Int = defaultDay, targetMonth:Int= defaultMonth, targetYear:Int = defaultYear): List<MyCalendarDisplayDate> {
        resetCalendarObject()
        setCalendarObject(targetDay, targetMonth, targetYear)
        val listMyCalendarDate = mutableListOf<MyCalendarDisplayDate>()
        for(y in calendarObject.get(Calendar.YEAR)..maximumYear){
            for(m in 0..11){
                listMyCalendarDate.add(
                    MyCalendarDisplayDate(
                        y,
                        monthName = getMonthName(m),
                        listDays = getListDays(m,y)
                    )
                )
            }
        }
        return listMyCalendarDate
    }
    private fun getListDays(monthIndex:Int,year:Int):List<MyCalendarDay>{
        val listDays = mutableListOf<MyCalendarDay>()
        setCalendarObject(month = monthIndex,year = year)
        val offsetDay = (calendarObject.get(Calendar.DAY_OF_WEEK) - 2 + 7 ) % 7
        repeat(offsetDay){
            listDays.add(
                MyCalendarDay(day = 0)
            )
        }
        for(dayIndex in 1..calendarObject.getActualMaximum(Calendar.DAY_OF_MONTH)){
            listDays.add(
                MyCalendarDay(
                    day = dayIndex,
                    isCurrent = dayIndex == currentDay && monthIndex == currentMonth && year == currentYear
                )
            )
        }
        return listDays
    }
    fun setCalendarObject(dayMonth:Int = defaultDay,month:Int = defaultMonth,year:Int = defaultYear){
        resetCalendarObject()
        calendarObject.set(Calendar.DAY_OF_MONTH,dayMonth)
        calendarObject.set(Calendar.MONTH,month)
        calendarObject.set(Calendar.YEAR,year)
    }
    private fun resetCalendarObject() = calendarObject.clear()
}