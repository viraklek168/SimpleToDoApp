package com.virak.simpletodoapp.di

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.virak.simpletodoapp.ui.calendar.CalendarViewModel

class CalendarViewModelFactory(
    private val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CalendarViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CalendarViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}