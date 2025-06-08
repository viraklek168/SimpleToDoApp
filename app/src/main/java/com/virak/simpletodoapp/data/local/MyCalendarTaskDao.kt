package com.virak.simpletodoapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MyCalendarTaskDao {
    @Insert
    suspend fun insert(user: MyCalendarTask): Long

    @Query("SELECT * FROM MyCalendarTask")
    suspend fun getAll(): List<MyCalendarTask>
}