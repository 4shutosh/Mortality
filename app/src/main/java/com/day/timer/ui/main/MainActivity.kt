package com.day.timer.ui.main

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.day.timer.ui.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlin.time.ExperimentalTime

/*
* created by : [Ashutosh Singh ; 4shutosh]
*/

/*Activity to initiate the compose UI, as an activity entry is required in the manifest file*/
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @ExperimentalTime
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MortalityApp()
        }
    }
}