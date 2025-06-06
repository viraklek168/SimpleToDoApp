package com.virak.simpletodoapp

import android.app.Application
import android.content.Context
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Application : Application() {
    companion object{
        lateinit var baseApplicationContext : Context
    }
    override fun onCreate() {
        super.onCreate()
        baseApplicationContext = this.applicationContext
    }
}