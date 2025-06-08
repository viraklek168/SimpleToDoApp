package com.virak.simpletodoapp.ui.calendar

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.virak.simpletodoapp.data.local.MyCalendarTask

data class MyCalendarDisplayDate(
    val year:Int,
    val monthName:String,
    val listDays:List<MyCalendarDay>
)

data class MyCalendarDay(
    val day:Int,
    val parentYear:Int,
    val parentMonth:Int,
    val isCurrent:Boolean = false,
    val listTasks:List<MyCalendarTask>? = null
)

data class ShowTask(
    val listTasks: List<MyCalendarTask>
)
