package com.virak.simpletodoapp.viewmodels

import androidx.lifecycle.ViewModel
import com.virak.simpletodoapp.MyCalendarDisplayDate
import com.virak.simpletodoapp.utils.CalendarManager
import com.virak.simpletodoapp.utils.CalendarManager.getDate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class CalendarViewModel : ViewModel(){
    private val _currentDisplayDate = MutableStateFlow(getDate())
    private val _currentDisplayDateIndex = MutableStateFlow(0 to true)
    val currentDisplayDate : StateFlow<List<MyCalendarDisplayDate>> = _currentDisplayDate
    val currentDisplayDateIndex : StateFlow<Pair<Int,Boolean>> = _currentDisplayDateIndex
    fun setCurrentTapIndex(action:CalendarManager.CalendarAction,index:Int? = null,isSmoothAnimate:Boolean = true){
        when(action){
            CalendarManager.CalendarAction.ClickNextMonth -> {
                _currentDisplayDateIndex.update {
                    val newValue = _currentDisplayDateIndex.value.first + 1
                    Pair(index ?: newValue,isSmoothAnimate)
                }
            }
            CalendarManager.CalendarAction.ClickPreviousMonth -> {
                _currentDisplayDateIndex.update {
                    val newValue = _currentDisplayDateIndex.value.first - 1
                    Pair(if(newValue < 0) 0 else index ?: newValue,isSmoothAnimate)
                }
            }
            CalendarManager.CalendarAction.ClickNeutralMonth -> {
                _currentDisplayDateIndex.update {
                    Pair(index?:0,isSmoothAnimate)
                }
            }
        }
    }
}