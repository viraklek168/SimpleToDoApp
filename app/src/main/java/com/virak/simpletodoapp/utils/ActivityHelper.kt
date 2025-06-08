package com.virak.simpletodoapp.utils

import android.content.Context
import android.content.Intent

object ActivityHelper {
    fun openActivity(currentActivity: Context, targetActivity:Class<*>){
        val intent = Intent(currentActivity,targetActivity)
        currentActivity.startActivity(intent)
    }
}