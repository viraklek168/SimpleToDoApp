package com.virak.simpletodoapp

data class MyCalendarDisplayDate(
    val year:Int,
    val monthName:String,
    val listDays:List<MyCalendarDay>
)

data class MyCalendarDay(
    val day:Int,
    val isCurrent:Boolean = false
)