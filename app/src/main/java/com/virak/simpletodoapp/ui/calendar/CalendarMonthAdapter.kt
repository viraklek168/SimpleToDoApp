package com.virak.simpletodoapp.ui.calendar

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.virak.simpletodoapp.databinding.LayoutItemMonthBinding

class CalendarMonthAdapter(
    private val listDisplayDate: MutableList<MyCalendarDisplayDate>
) : RecyclerView.Adapter<CalendarMonthAdapter.CalendarMonthViewHolder>() {

    inner class CalendarMonthViewHolder(private val binding: LayoutItemMonthBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(myDisplayDate: MyCalendarDisplayDate) {
            binding.rvDays.apply {
                layoutManager = GridLayoutManager(this.context,7)
                adapter = CalendarDayAdapter(myDisplayDate.listDays)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarMonthViewHolder {
        val binding = LayoutItemMonthBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CalendarMonthViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CalendarMonthViewHolder, position: Int) {
        holder.bind(listDisplayDate[position])
    }

    fun updateData(newList:List<MyCalendarDisplayDate>){
        listDisplayDate.clear()
        listDisplayDate.addAll(newList)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = listDisplayDate.size
}
