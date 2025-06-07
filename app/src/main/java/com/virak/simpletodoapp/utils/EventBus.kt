package com.virak.simpletodoapp.utils

object SimpleEventBus {
    private val listeners = mutableListOf<(Any) -> Unit>()

    fun post(event: Any) {
        listeners.forEach { it(event) }
    }

    fun subscribe(listener: (Any) -> Unit) {
        listeners.add(listener)
    }

    fun unsubscribe(listener: (Any) -> Unit) {
        listeners.remove(listener)
    }
}