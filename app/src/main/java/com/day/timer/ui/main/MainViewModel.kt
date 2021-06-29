package com.day.timer.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {


    private val _timerText = MutableLiveData<String>()
    val timerText: LiveData<String> get() = _timerText

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _toast: MutableLiveData<String> = MutableLiveData()
    val toast: LiveData<String> get() = _toast

    init {
//        startBackgroundServiceTimer(1624986116000)
    }

    fun startBackgroundServiceTimer(futureTime: Long) {

//        var differenceMicro = futureTimeStamp.minus(currentTime).inMicroseconds.toLong()
//        val difference = futureTimeStamp.minus(currentTime).inWholeHours.toDouble()

//        val timer = object : CountDownTimer(differenceMicro, 100) {
//            override fun onTick(millisUntilFinished: Long) {
//                updateText(millisUntilFinished)
//            }
//
//            override fun onFinish() {
//
//            }
//        }
//        timer.start()
        // making default time difference unit as hours
    }

    private fun updateText(millisUntilFinished: Long) {
        val inHours = TimeUnit.MILLISECONDS.toHours(millisUntilFinished)
        _timerText.postValue(inHours.toString())
    }
}