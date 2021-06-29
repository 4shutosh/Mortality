package com.day.timer.ui.main

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity

/*
* created by : [Ashutosh Singh ; 4shutosh]
*/

/*Activity to initiate the compose UI, as an activity entry is required in the manifest file*/
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MortalityApp()
        }
    }
}