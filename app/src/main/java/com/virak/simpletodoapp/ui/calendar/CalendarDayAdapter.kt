package com.virak.simpletodoapp.ui.calendar

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.virak.simpletodoapp.R
import com.virak.simpletodoapp.databinding.ItemCalendarCellBinding
import com.virak.simpletodoapp.utils.SimpleEventBus

class CalendarDayAdapter(
    private val listDays: List<MyCalendarDay>
) : RecyclerView.Adapter<CalendarDayAdapter.CalendarDayViewHolder>() {

    inner class CalendarDayViewHolder(private val binding: ItemCalendarCellBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(day: MyCalendarDay) {
            binding.dotsContainer.removeAllViews() // Clear any recycled views
            binding.tvLabel.text = if(day.day == 0){
                ""
            } else {
                day.day.toString()
            }
            binding.tvLabel.setOnClickListener {
                if(day.day == 0) return@setOnClickListener
                if(day.listTasks?.isNotEmpty() == true){
                    SimpleEventBus.post(ShowTask(day.listTasks))
                }else{
                    SimpleEventBus.post(day)
                }
            }
            if(day.isCurrent){
                binding.tvLabel.setBackgroundResource(R.drawable.bg_current_day)
            }
            if(day.listTasks?.isNotEmpty() == true){
                val dotContainer = binding.dotsContainer
                dotContainer.removeAllViews() // Clear any recycled views

                val taskCount = day.listTasks?.size ?: 0
                val dotCount = minOf(6, taskCount)

                repeat(dotCount) {
                    val dot = View(binding.dotsContainer.context).apply {
                        layoutParams = LinearLayout.LayoutParams(24, 24).apply {
                            (this as? LinearLayout.LayoutParams)?.setMargins(2, 0, 2, 0)
                        }
                        background = ContextCompat.getDrawable(context, R.drawable.bg_dot)
                    }
                    dotContainer.addView(dot)
                }
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