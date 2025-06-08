package com.virak.simpletodoapp.utils.extensions

import com.virak.simpletodoapp.Application

fun Int.string() = Application.baseApplicationContext.getString(this)