package com.example.demoapp.common

import android.app.Application
import android.content.Context

class DemoApp : Application() {
    companion object {
        var instance: DemoApp? = null
        fun context(): Context = instance!!.applicationContext
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

}