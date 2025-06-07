package com.virak.simpletodoapp.utils.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.virak.simpletodoapp.MyCalendarDay
import com.virak.simpletodoapp.R
import com.virak.simpletodoapp.databinding.ItemCalendarCellBinding
import com.virak.simpletodoapp.utils.DayClickedEvent
import com.virak.simpletodoapp.utils.SimpleEventBus

class CalendarDayAdapter(
    private val listDays: List<MyCalendarDay>
) : RecyclerView.Adapter<CalendarDayAdapter.CalendarDayViewHolder>() {

    inner class CalendarDayViewHolder(private val binding: ItemCalendarCellBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(day: MyCalendarDay) {
            binding.tvLabel.text = if(day.day == 0) "" else day.day.toString()
            binding.tvLabel.setOnClickListener {
                SimpleEventBus.post(DayClickedEvent(day = day.day.toString()))
            }
            if(day.isCurrent){
                binding.tvLabel.setBackgroundResource(R.drawable.bg_current_day)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarDayViewHolder {
        val binding = ItemCalendarCellBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CalendarDayViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CalendarDayViewHolder, position: Int) {
        holder.bind(listDays[position])
    }

    override fun getItemCount(): Int = listDays.size
}