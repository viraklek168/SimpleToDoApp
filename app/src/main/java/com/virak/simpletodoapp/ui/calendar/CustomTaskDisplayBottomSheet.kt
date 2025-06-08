package com.virak.simpletodoapp.ui.calendar

import android.content.DialogInterface
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.virak.simpletodoapp.data.local.MyCalendarTask
import com.virak.simpletodoapp.databinding.LayoutCustomSimpleTaskDisplayBinding


class CustomTaskDisplayBottomSheet : BottomSheetDialogFragment() {
    lateinit var binding : LayoutCustomSimpleTaskDisplayBinding
    lateinit var listTasks : List<MyCalendarTask>
    private var onDismiss: () -> Unit = { }

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
        return LayoutCustomSimpleTaskDisplayBinding.inflate(layoutInflater,null,false).apply {
            binding = this
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.rvTaskDisplay.apply {
            adapter = CalendarShowTaskAdapter(listTasks)
            layoutManager = LinearLayoutManager(context)
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        onDismiss.invoke()
    }
    fun setOnDismiss(onDismiss:()->Unit){
        this.onDismiss = onDismiss
    }
    fun showBottomSheet(
        fragmentManager:FragmentManager,
        listTask:List<MyCalendarTask>
    ){
        show(fragmentManager, CustomTaskDisplayBottomSheet::class.java.simpleName)
        this.listTasks = listTask
    }
}