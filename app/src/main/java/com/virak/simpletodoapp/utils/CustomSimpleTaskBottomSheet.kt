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
import com.virak.simpletodoapp.BR
import com.virak.simpletodoapp.R
import com.virak.simpletodoapp.databinding.LayoutCustomSimpleTaskBottomSheetBinding
import com.virak.simpletodoapp.databinding.LayoutCustomSimpleWheelPickerBinding
import com.virak.simpletodoapp.utils.adapter.SimpleAdapter
import kotlin.math.abs


class CustomSimpleTaskBottomSheet : BottomSheetDialogFragment() {
    lateinit var binding : LayoutCustomSimpleTaskBottomSheetBinding
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
        return LayoutCustomSimpleTaskBottomSheetBinding.inflate(layoutInflater,null,false).apply {
            binding = this
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

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
    ){
        show(fragmentManager,CustomSimpleTaskBottomSheet::class.java.simpleName)
    }
}