package com.virak.simpletodoapp

import androidx.room.Entity
import androidx.room.PrimaryKey

data class MyCalendarDisplayDate(
    val year:Int,
    val monthName:String,
    val listDays:List<MyCalendarDay>
)

data class MyCalendarDay(
    val day:Int,
    val parentYear:Int,
    val parentMonth:Int,
    val isCurrent:Boolean = false
)

@Entity
data class MyCalendarTask(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val taskName:String,
    val taskDescription:String,
    val taskDate:Long
)