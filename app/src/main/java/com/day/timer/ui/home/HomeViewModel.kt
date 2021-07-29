package com.day.timer.ui.home

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.day.timer.data.TimeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.ExperimentalTime

@ExperimentalTime
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val timeRepository: TimeRepository
) : ViewModel() {


    private val _timerText = MutableLiveData<String>()
    val timerText: LiveData<String> get() = _timerText

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _toast: MutableLiveData<String> = MutableLiveData()
    val toast: LiveData<String> get() = _toast

    init {
        viewModelScope.launch(Dispatchers.Main) {
            startBackgroundServiceTimer()
        }
    }


    @ExperimentalTime
    suspend fun startBackgroundServiceTimer() {

        val timeInLong = timeRepository.getDeathTime()
        if (timeInLong == 0L) {
            val oneHourInstant: Duration = Duration.Companion.hours(1)

            val currentTime = Clock.System.now()
            val oneHourInFuture = currentTime + oneHourInstant

            val oneHourInFutureLong = oneHourInFuture.toEpochMilliseconds()
            timeRepository.setDeathTime(oneHourInFutureLong)

            // todo apply a when checking the mode selected by the user and then start the appropriate timer
            startTimer(oneHourInstant.inWholeMilliseconds)
        } else {
            _toast.postValue("Something found $timeInLong")
        }

    }

    // create a background thing a service or work manager for this
    private fun startTimer(timerMillis: Long) {
        object : CountDownTimer(timerMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                updateTextHours(millisUntilFinished)
            }

            override fun onFinish() {

            }
        }.start()
    }


    private fun updateTextMicros(timeMilliSeconds: Long) {
        val timerMicros = TimeUnit.MILLISECONDS.toMicros(timeMilliSeconds)
        _timerText.postValue(timerMicros.toString())
    }

    private fun updateTextHours(timeMilliSeconds: Long) {
        val textDuration = Duration.Companion.convert(
            timeMilliSeconds.toDouble(),
            sourceUnit = DurationUnit.MILLISECONDS,
            targetUnit = DurationUnit.HOURS
        )
        val textInMinutes = TimeUnit.MILLISECONDS.toMinutes(timeMilliSeconds)
        _timerText.postValue(textInMinutes.toString())
    }

    private fun actionOnFinishTimer() {
        _timerText.postValue("Done")
    }
}