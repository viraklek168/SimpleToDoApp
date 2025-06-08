package com.virak.simpletodoapp.ui.calendar

import android.app.Application
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.virak.simpletodoapp.data.local.AppDatabase
import com.virak.simpletodoapp.data.local.MyCalendarTask
import com.virak.simpletodoapp.utils.CalendarDataGenerator
import com.virak.simpletodoapp.utils.CalendarDataGenerator.getDate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Locale

class CalendarViewModel(application: Application) : ViewModel(){
    var isFirstLoad = true
    private val _currentDisplayDate = MutableStateFlow(emptyList<MyCalendarDisplayDate>())
    private val _currentDisplayDateIndex = MutableStateFlow(-1 to true)
    val currentDisplayDate : StateFlow<List<MyCalendarDisplayDate>> = _currentDisplayDate
    val currentDisplayDateIndex : StateFlow<Pair<Int,Boolean>> = _currentDisplayDateIndex
    private val taskDao = AppDatabase.getDatabase(application).taskDao()
    fun setCurrentTapIndex(action:CalendarDataGenerator.CalendarAction,index:Int? = null,isSmoothAnimate:Boolean = true){
        when(action){
            CalendarDataGenerator.CalendarAction.ClickNextMonth -> {
                _currentDisplayDateIndex.update {
                    val newValue = _currentDisplayDateIndex.value.first + 1
                    Pair(index ?: newValue,isSmoothAnimate)
                }
            }
            CalendarDataGenerator.CalendarAction.ClickPreviousMonth -> {
                _currentDisplayDateIndex.update {
                    val newValue = _currentDisplayDateIndex.value.first - 1
                    Pair(if(newValue < 0) 0 else index ?: newValue,isSmoothAnimate)
                }
            }
            CalendarDataGenerator.CalendarAction.ClickNeutralMonth -> {
                _currentDisplayDateIndex.update {
                    Pair(index?:0,isSmoothAnimate)
                }
            }
            CalendarDataGenerator.CalendarAction.UpdateData -> {
                viewModelScope.launch {
                    delay(1500)
                    _currentDisplayDateIndex.update {
                        val newValue = _currentDisplayDateIndex.value.first
                        Pair(newValue,isSmoothAnimate)
                    }
                }
            }
        }
    }
    fun getCalendarData(){
        viewModelScope.launch(Dispatchers.Default) {
            val calendarGeneratedData = getDate()
            val tasks = taskDao.getAll()
            mergeTasksIntoCalendar(calendarGeneratedData,tasks)
        }
    }
    private fun mergeTasksIntoCalendar(
        calendarList: List<MyCalendarDisplayDate>,
        tasks: List<MyCalendarTask>
    ) {
        val monthFormat = SimpleDateFormat("MMMM", Locale.getDefault())

        // Group tasks by (year, monthName, day)
        val taskMap = tasks.groupBy { task ->
            val cal = Calendar.getInstance().apply { timeInMillis = task.taskDate }
            Triple(
                cal.get(Calendar.YEAR),
                monthFormat.format(cal.time),
                cal.get(Calendar.DAY_OF_MONTH)
            )
        }

        // Rebuild calendar with tasks inserted
        val newCalendarList = calendarList.map { displayDate ->
            val updatedDays = displayDate.listDays.map { day ->
                val tasksForDay = taskMap[Triple(day.parentYear, displayDate.monthName, day.day)].orEmpty()
                day.copy(listTasks = tasksForDay)
            }
            displayDate.copy(listDays = updatedDays)
        }
        _currentDisplayDate.value = newCalendarList
        if(isFirstLoad){
            setCurrentTapIndex(CalendarDataGenerator.CalendarAction.ClickNextMonth,
                currentDisplayDate.value.indexOf(
                    currentDisplayDate.value.find {
                        it.listDays.firstOrNull { it.isCurrent }?.isCurrent == true
                    }
                ),
                false
            )
            isFirstLoad = false
        }else{
            setCurrentTapIndex(
                CalendarDataGenerator.CalendarAction.UpdateData,
                isSmoothAnimate = false
            )
        }
    }
    fun addTask(task: MyCalendarTask) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = taskDao.insert(task)
            if (result != -1L) {
                Log.d("InsertResult", "Insert successful with id: $result")
                getCalendarData()
            } else {
                Log.e("InsertResult", "Insert failed")
            }
        }
    }
}