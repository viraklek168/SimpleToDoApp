package com.virak.simpletodoapp.utils

import android.content.DialogInterface
import android.content.res.Resources
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.virak.simpletodoapp.BR
import com.virak.simpletodoapp.MyCalendarTask
import com.virak.simpletodoapp.R
import com.virak.simpletodoapp.databinding.LayoutCustomSimpleTaskBottomSheetBinding
import com.virak.simpletodoapp.databinding.LayoutCustomSimpleWheelPickerBinding
import com.virak.simpletodoapp.utils.adapter.SimpleAdapter
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
import java.util.Locale
import kotlin.math.abs


class CustomSimpleTaskBottomSheet : BottomSheetDialogFragment() {
    lateinit var binding : LayoutCustomSimpleTaskBottomSheetBinding
    private var onDismiss: (MyCalendarTask) -> Unit = { }
    private var currentSelectDate = ""
    private val timePicker by lazy {
        MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_12H)
            .setHour(12)
            .setMinute(10)
            .setTitleText("Select Appointment time")
            .build()
    }

    override fun onStart() {
        super.onStart()
        val bottomSheet =
            dialog?.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
        bottomSheet?.let {
            val behavior = BottomSheetBehavior.from(it)
            val screenHeight = Resources.getSystem().displayMetrics.heightPixels
            val desiredHeight =  (screenHeight * 0.5).toInt()
            behavior.peekHeight = desiredHeight

            // Optionally expand immediately
            behavior.state = BottomSheetBehavior.STATE_COLLAPSED

            // Set layout height directly
            it.layoutParams.height = desiredHeight
            it.requestLayout()
        }

    }
        override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return LayoutCustomSimpleTaskBottomSheetBinding.inflate(layoutInflater,null,false).apply {
            binding = this
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.btnCreate.setOnClickListener {
            dismiss()
        }
        binding.tvTaskTimeEditText.setOnClickListener {
            timePicker.show(parentFragmentManager,MaterialTimePicker::class.java.simpleName)
        }
        timePicker.addOnPositiveButtonClickListener {
            val hour = timePicker.hour  // 0–23
            val minute = timePicker.minute

            // Convert to 12-hour format
            val amPm = if (hour < 12) "AM" else "PM"
            val hour12 = when {
                hour == 0 -> 12
                hour > 12 -> hour - 12
                else -> hour
            }

            val timeString = String.format("%02d:%02d %s", hour12, minute, amPm)
            binding.tvTaskTimeEditText.setText(timeString)
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        onDismiss.invoke(
            MyCalendarTask(
             taskName = binding.tvTaskName.editText?.text.toString(),
                taskDescription = binding.tvTaskDescriptionEditText.text.toString(),
                taskDate = getTaskDate(
                    dateString = currentSelectDate,
                    timeString = binding.tvTaskTimeEditText.text.toString()
                )
        )
        )
        binding.tvTaskNameEditText.setText("")
        binding.tvTaskDescriptionEditText.setText("")
        binding.tvTaskTimeEditText.setText("")
        currentSelectDate = ""
    }
    private fun getTaskDate(
        dateString:String,
        timeString:String
    ):Long{
// Step 1: Parse date string
        val dateFormat = SimpleDateFormat("d MMMM yyyy", Locale.US)
        val date: Date = dateFormat.parse(dateString)!!

// Step 2: Parse time string
        val timeFormat = SimpleDateFormat("hh:mm a", Locale.US)
        val time: Date = timeFormat.parse(timeString)!!

// Step 3: Combine date and time using Calendar
        val calendarDate = Calendar.getInstance()
        calendarDate.time = date

        val calendarTime = Calendar.getInstance()
        calendarTime.time = time

// Set hour and minute from time to date calendar
        calendarDate.set(Calendar.HOUR_OF_DAY, calendarTime.get(Calendar.HOUR_OF_DAY))
        calendarDate.set(Calendar.MINUTE, calendarTime.get(Calendar.MINUTE))
        calendarDate.set(Calendar.SECOND, 0)
        calendarDate.set(Calendar.MILLISECOND, 0)

// Step 4: Get epoch millis
        val epochMillis = calendarDate.timeInMillis
        return epochMillis
    }
    fun setOnDismiss(onDismiss:(MyCalendarTask)->Unit){
        this.onDismiss = onDismiss
    }
    fun showBottomSheet(
        fragmentManager:FragmentManager,
        day:String,
        month:String,
        year:String
    ){
        show(fragmentManager,CustomSimpleTaskBottomSheet::class.java.simpleName)
        currentSelectDate = "$day $month $year"
    }
}