package com.virak.simpletodoapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MyCalendarTask(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val taskName:String,
    val taskDescription:String,
    val taskDate:Long
)