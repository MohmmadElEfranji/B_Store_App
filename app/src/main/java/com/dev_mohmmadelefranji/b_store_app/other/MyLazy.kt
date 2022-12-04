package com.dev_mohmmadelefranji.b_store_app.other

import kotlin.reflect.KProperty

class MyLazy<out T : Any>(
    private val initialize: () -> T
) {
    val s by lazy { }
    private var value: T? = null

    operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return if (value == null) {
            value = initialize()
            value!!

        } else value!!
    }

}