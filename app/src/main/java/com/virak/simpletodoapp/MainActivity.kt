package com.virak.simpletodoapp

import android.os.Build
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import android.view.animation.AnimationUtils
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.virak.simpletodoapp.databinding.ActivityMainBinding
import com.virak.simpletodoapp.di.CalendarViewModelFactory
import com.virak.simpletodoapp.ui.calendar.CustomSimpleTaskBottomSheet
import com.virak.simpletodoapp.ui.calendar.CustomSimpleWheelPicker
import com.virak.simpletodoapp.utils.SimpleEventBus
import com.virak.simpletodoapp.ui.calendar.CalendarMonthAdapter
import com.virak.simpletodoapp.utils.getMonthName
import com.virak.simpletodoapp.ui.calendar.CalendarViewModel
import com.virak.simpletodoapp.ui.calendar.CustomTaskDisplayBottomSheet
import com.virak.simpletodoapp.ui.calendar.MyCalendarDay
import com.virak.simpletodoapp.ui.calendar.ShowTask
import com.virak.simpletodoapp.utils.CalendarDataGenerator
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding
    private val calendarAdapter by lazy {
        CalendarMonthAdapter(mutableListOf())
    }
    private val calendarViewModel: CalendarViewModel by viewModels{
        CalendarViewModelFactory(application)
    }
    private val dateWheelPicker by lazy {
        CustomSimpleWheelPicker()
    }
    private val taskBottomsheet by lazy {
        CustomSimpleTaskBottomSheet()
    }
    private val showTaskBottomsheet by lazy {
        CustomTaskDisplayBottomSheet()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
        initDataBinding()
        observeEventClick()
        calendarViewModel.getCalendarData()
    }

    private fun initDataBinding(){
        binding.viewpager2.apply {
            adapter = calendarAdapter
        }
    }

    private fun observeEventClick(){
        SimpleEventBus.subscribe {
            if(it is MyCalendarDay){
                taskBottomsheet.setOnAddTask {
                    calendarViewModel.addTask(it)
                }
                taskBottomsheet.showBottomSheet(
                    supportFragmentManager,
                    day = it.day.toString(),
                    month = getMonthName(it.parentMonth),
                    year = it.parentYear.toString()
                )
            }else if(it is ShowTask){
                showTaskBottomsheet.showBottomSheet(
                    supportFragmentManager,
                    it.listTasks,
                )
            }
        }
        binding.tvHeaderTitle.setOnClickListener {
            val currentDisplayMonth = calendarViewModel.currentDisplayDate.value[calendarViewModel.currentDisplayDateIndex.value.first]
            dateWheelPicker.setOnDismiss{month,year->
                val selectDateObject = calendarViewModel.currentDisplayDate.value.find {
                    it.monthName == month && it.year == year
                }
                val indexOfSelectDateObject = calendarViewModel.currentDisplayDate.value.indexOf(selectDateObject)
                calendarViewModel.setCurrentTapIndex(CalendarDataGenerator.CalendarAction.ClickNeutralMonth, index = indexOfSelectDateObject )
            }
            dateWheelPicker.showPicker(supportFragmentManager,currentDisplayMonth.monthName, currentYear = currentDisplayMonth.year)
        }
        binding.btnNext.setOnClickListener {
            calendarViewModel.setCurrentTapIndex(CalendarDataGenerator.CalendarAction.ClickNextMonth)
        }
        binding.btnPrevious.setOnClickListener {
            calendarViewModel.setCurrentTapIndex(CalendarDataGenerator.CalendarAction.ClickPreviousMonth)
        }
        lifecycleScope.launch {
            calendarViewModel.currentDisplayDate.collect{
                if(it.isNotEmpty()){
                    binding.viewpager2.apply {
                        calendarAdapter.updateData(it.toMutableList())
                    }
                }
            }
        }
        lifecycleScope.launch {
            calendarViewModel.currentDisplayDateIndex.collect{
                if(it.first == -1) return@collect
                binding.tvHeaderTitle.text = getString(R.string.month_display_header_title,
                    calendarViewModel.currentDisplayDate.value[it.first].monthName,
                    calendarViewModel.currentDisplayDate.value[it.first].year)
                binding.viewpager2.setCurrentItem(it.first,it.second)
            }
        }
    }

    private fun init(){
        enableEdgeToEdge()
        setContentView(
            ActivityMainBinding.inflate(layoutInflater).apply {
            binding = this
        }.root
        )
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        animateSplashScreenExit()
    }

    private fun animateSplashScreenExit() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            installSplashScreen().setKeepOnScreenCondition{
                calendarViewModel.currentDisplayDate.value.isEmpty()
            }
            splashScreen.setOnExitAnimationListener { splashScreenView ->
                val fadeOut = AnimationUtils.loadAnimation(this, R.anim.slide_left_exit)
                splashScreenView.startAnimation(fadeOut)
                fadeOut.setAnimationListener(object : AnimationListener {
                    override fun onAnimationStart(p0: Animation?) {

                    }

                    override fun onAnimationEnd(p0: Animation?) {
                        splashScreenView.remove()
                    }

                    override fun onAnimationRepeat(p0: Animation?) {

                    }

                })
            }
        }
    }
}

