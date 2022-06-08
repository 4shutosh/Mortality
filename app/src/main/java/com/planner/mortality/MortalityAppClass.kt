package com.planner.mortality

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import logcat.AndroidLogcatLogger

@HiltAndroidApp
class MortalityAppClass : Application() {


    override fun onCreate() {
        super.onCreate()

        AndroidLogcatLogger.installOnDebuggableApp(this)
    }
}