package com.virak.simpletodoapp.utils

import android.content.DialogInterface
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
import com.virak.simpletodoapp.BR
import com.virak.simpletodoapp.R
import com.virak.simpletodoapp.databinding.LayoutCustomSimpleWheelPickerBinding
import com.virak.simpletodoapp.utils.adapter.SimpleAdapter
import kotlin.math.abs


class CustomSimpleWheelPicker : BottomSheetDialogFragment() {
    lateinit var binding : LayoutCustomSimpleWheelPickerBinding
    private val listMonths = listOf(
        "",
        "",
        R.string.month_january.string(),
        R.string.month_february.string(),
        R.string.month_march.string(),
        R.string.month_april.string(),
        R.string.month_may.string(),
        R.string.month_june.string(),
        R.string.month_july.string(),
        R.string.month_august.string(),
        R.string.month_september.string(),
        R.string.month_october.string(),
        R.string.month_november.string(),
        R.string.month_december.string(),
        "",
        ""
    )
    private val listYears = listOf("","") + (1970..2100).map { it.toString() }.toList() + listOf("","")
    private var currentMonthPosition = 6
    private var currentYearPosition = 12
    private var currentSelectYear = 0
    private var currentSelectMonth = ""
    private var onDismiss: (String, Int) -> Unit = { m: String, y: Int -> }

    override fun onStart() {
        super.onStart()
        val bottomSheet =
            dialog?.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
        bottomSheet?.let {
            val behavior = BottomSheetBehavior.from(it)

            val desiredHeight = 400
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
        return LayoutCustomSimpleWheelPickerBinding.inflate(layoutInflater,null,false).apply {
            binding = this
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.rvMonth.apply {
            isNestedScrollingEnabled = false
            layoutManager = LinearLayoutManager(this.context)
            addItemDecoration(SpaceItemDecoration(30))
            LinearSnapHelper().attachToRecyclerView(this)
            adapter = SimpleAdapter<String>(
                items = listMonths,
                layoutId = R.layout.layout_item_custom_simple_wheel_picker_month,
                bindVariableId = BR.month,
                onItemClick = { user, _ -> }
            )

            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    for(i in 0..(recyclerView.childCount - 1)){
                        val child = recyclerView.getChildAt(i)
                        val threshold = 20
                        val centerRecyclerView = recyclerView.height / 2
                        val centerChild = (child.top + child.bottom) / 2
                        val distance = abs(centerRecyclerView - centerChild)
                        val scale = 1.2f - (distance/threshold).coerceAtMost(1) * 0.5f
                        child.scaleX = scale
                        child.scaleY = scale
                        child.alpha = scale
                        if(scale == 1.2f){
                            if(child is TextView){
                                currentSelectMonth = child.text.toString()
                            }
                        }
                    }
                }

                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                }
            })

            post {
                for(i in 0..(this.childCount - 1)){
                    val child = this.getChildAt(i)
                    val threshold = 40
                    val centerRecyclerView = this.height / 2
                    val centerChild = (child.top + child.bottom) / 2
                    val distance = abs(centerRecyclerView - centerChild)
                    val scale = 1.2f - (distance/threshold).coerceAtMost(1) * 0.5f
                    child.scaleX = scale
                    child.scaleY = scale
                    child.alpha = scale
                    val layoutManager = layoutManager as LinearLayoutManager
                    val offset = centerRecyclerView - 20
                    layoutManager.scrollToPositionWithOffset(currentMonthPosition, offset) // Item at position 5 appears 100px below top
                    if(scale == 1.2f){
                        if(child is TextView){
                            currentSelectMonth = child.text.toString()
                        }
                    }
                }
            }
        }
        binding.rvYear.apply {
            isNestedScrollingEnabled = false
            layoutManager = LinearLayoutManager(this.context)
            addItemDecoration(SpaceItemDecoration(30))
            LinearSnapHelper().attachToRecyclerView(this)
            adapter = SimpleAdapter<String>(
                items = listYears,
                layoutId = R.layout.layout_item_custom_simple_wheel_picker_month,
                bindVariableId = BR.month,
                onItemClick = { user, _ -> }
            )

            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    for(i in 0..(recyclerView.childCount - 1)){
                        val child = recyclerView.getChildAt(i)
                        val threshold = 20
                        val centerRecyclerView = recyclerView.height / 2
                        val centerChild = (child.top + child.bottom) / 2
                        val distance = abs(centerRecyclerView - centerChild)
                        val scale = 1.2f - (distance/threshold).coerceAtMost(1) * 0.5f
                        child.scaleX = scale
                        child.scaleY = scale
                        child.alpha = scale
                        if(scale == 1.2f){
                            if(child is TextView){
                                currentSelectYear = child.text.toString().toInt()
                            }
                        }
                    }
                }

                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                }
            })

            post {
                for(i in 0..(this.childCount - 1)){
                    val child = this.getChildAt(i)
                    val threshold = 40
                    val centerRecyclerView = this.height / 2
                    val centerChild = (child.top + child.bottom) / 2
                    val distance = abs(centerRecyclerView - centerChild)
                    val scale = 1.2f - (distance/threshold).coerceAtMost(1) * 0.5f
                    child.scaleX = scale
                    child.scaleY = scale
                    child.alpha = scale
                    val layoutManager = layoutManager as LinearLayoutManager
                    val offset = centerRecyclerView - 20
                    layoutManager.scrollToPositionWithOffset(currentYearPosition, offset)
                    if(scale == 1.2f){
                        if(child is TextView){
                            currentSelectYear = child.text.toString().toInt()
                        }
                    }
                }
            }
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        onDismiss.invoke(currentSelectMonth,currentSelectYear)
    }
    fun setOnDismiss(onDismiss:(String,Int)->Unit){
        this.onDismiss = onDismiss
    }
    fun showPicker(
        fragmentManager:FragmentManager,
        currentMonth:String,
        currentYear:Int
    ){
        show(fragmentManager,CustomSimpleWheelPicker::class.java.simpleName)
        this.currentMonthPosition = listMonths.indexOf(currentMonth)
        this.currentYearPosition = listYears.indexOf(currentYear.toString())
    }
}