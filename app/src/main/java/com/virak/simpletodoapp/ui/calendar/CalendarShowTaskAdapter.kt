package com.virak.simpletodoapp.ui.calendar

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.virak.simpletodoapp.R
import com.virak.simpletodoapp.data.local.MyCalendarTask
import com.virak.simpletodoapp.databinding.ItemCalendarCellBinding
import com.virak.simpletodoapp.databinding.LayoutItemCustomSimpleTaskDisplayBinding
import com.virak.simpletodoapp.utils.SimpleEventBus

class CalendarShowTaskAdapter(
    private val listTasks: List<MyCalendarTask>
) : RecyclerView.Adapter<CalendarShowTaskAdapter.CalendarShowTasViewHolder>() {

    inner class CalendarShowTasViewHolder(private val binding: LayoutItemCustomSimpleTaskDisplayBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(task: MyCalendarTask) {
          binding.tvTaskNameText.text = task.taskName
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarShowTasViewHolder {
        val binding = LayoutItemCustomSimpleTaskDisplayBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CalendarShowTasViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CalendarShowTasViewHolder, position: Int) {
        holder.bind(listTasks[position])
    }

    override fun getItemCount(): Int = listTasks.size
}