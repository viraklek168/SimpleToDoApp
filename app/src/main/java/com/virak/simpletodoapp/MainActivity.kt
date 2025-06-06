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
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.virak.simpletodoapp.databinding.ActivityMainBinding
import com.virak.simpletodoapp.utils.CalendarManager
import com.virak.simpletodoapp.utils.CustomSimpleWheelPicker
import com.virak.simpletodoapp.utils.adapter.CalendarMonthAdapter
import com.virak.simpletodoapp.viewmodels.CalendarViewModel
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding
    private val calendarViewModel: CalendarViewModel by viewModels()
    private val dateWheelPicker by lazy {
        CustomSimpleWheelPicker()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
        initDataBinding()
        observeEventClick()
    }

    private fun initDataBinding(){
        binding.viewpager2.apply {
            offscreenPageLimit = 12
            adapter = CalendarMonthAdapter(calendarViewModel.currentDisplayDate.value)
        }
        calendarViewModel.setCurrentTapIndex(CalendarManager.CalendarAction.ClickNextMonth,
            calendarViewModel.currentDisplayDate.value.indexOf(
                calendarViewModel.currentDisplayDate.value.find {
                    it.listDays.firstOrNull { it.isCurrent }?.isCurrent == true
                }
            ),
            false
        )
    }

    private fun observeEventClick(){
        binding.layoutHeader.setOnClickListener {
            val currentDisplayMonth = calendarViewModel.currentDisplayDate.value[calendarViewModel.currentDisplayDateIndex.value.first]
            dateWheelPicker.showPicker(supportFragmentManager,currentDisplayMonth.monthName, currentYear = currentDisplayMonth.year)
        }
        binding.btnNext.setOnClickListener {
            calendarViewModel.setCurrentTapIndex(CalendarManager.CalendarAction.ClickNextMonth)
        }
        binding.btnPrevious.setOnClickListener {
            calendarViewModel.setCurrentTapIndex(CalendarManager.CalendarAction.ClickPreviousMonth)
        }
        lifecycleScope.launch {
            calendarViewModel.currentDisplayDate.collect{
                binding.viewpager2.apply {
                    offscreenPageLimit = 12
                    adapter = CalendarMonthAdapter(calendarViewModel.currentDisplayDate.value)
                }
            }
        }
        lifecycleScope.launch {
            calendarViewModel.currentDisplayDateIndex.collect{
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

