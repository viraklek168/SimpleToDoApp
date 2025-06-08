package com.virak.simpletodoapp.utils

import android.icu.util.Calendar
import com.virak.simpletodoapp.ui.calendar.MyCalendarDay
import com.virak.simpletodoapp.ui.calendar.MyCalendarDisplayDate

object CalendarDataGenerator{
    sealed class CalendarAction{
        object ClickNextMonth : CalendarAction()
        object ClickPreviousMonth : CalendarAction()
        object ClickNeutralMonth : CalendarAction()
        object UpdateData : CalendarAction()
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
                MyCalendarDay(day = 0, parentMonth = 0, parentYear = 0)
            )
        }
        for(dayIndex in 1..calendarObject.getActualMaximum(Calendar.DAY_OF_MONTH)){
            listDays.add(
                MyCalendarDay(
                    day = dayIndex,
                    parentYear = year,
                    parentMonth = monthIndex,
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