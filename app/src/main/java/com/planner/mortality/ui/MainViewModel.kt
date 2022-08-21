package com.planner.mortality.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.planner.mortality.data.dao.UserDataDao
import com.planner.mortality.utils.AppCoroutineDispatcher
import com.planner.mortality.utils.MortalityTime
import com.planner.mortality.utils.MortalityTimeConsumedPercentage
import com.planner.mortality.utils.SingleLiveEvent
import com.planner.mortality.utils.calculateDeathPercentages
import com.planner.mortality.utils.extensions.toLiveData
import com.planner.mortality.utils.getFormattedDate
import com.planner.mortality.utils.getMortalityTimeDifference
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import logcat.logcat
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val userDataDao: UserDataDao,
    private val appCoroutineDispatcher: AppCoroutineDispatcher,
) : ViewModel() {

    private val _deathTimerText = MutableLiveData<MortalityTime>()
    val deathTimerText = _deathTimerText.toLiveData()

    private val _deathTimerPercentage = MutableLiveData<MortalityTimeConsumedPercentage>()
    val deathTimerPercentage = _deathTimerPercentage.toLiveData()

    private val _command = SingleLiveEvent<Command>()
    val command = _command.toLiveData()

    sealed class Command {
        class ShowToast(val message: String) : Command()
    }

    private var userDeathTimeStamp: Long = -1L

    init {
        startUserDeathTimer()
    }


    private fun startUserDeathTimer() {
        viewModelScope.launch(appCoroutineDispatcher.io) {
            val userData = userDataDao.getUserData()
            if (userData?.dateOfBirthTimeStamp != null) {
                userDeathTimeStamp = userData.deathTimestamp
                while (true) {
                    val mortalityTime = getMortalityTimeDifference(userDeathTimeStamp)
                    _deathTimerText.postValue(mortalityTime)
                    _deathTimerPercentage.postValue(mortalityTime.calculateDeathPercentages(userData.lifeExpectancyYears))
                    delay(500)
                }
            }
        }
    }

    fun actionUserDeathTimerClicked() {
        viewModelScope.launch(appCoroutineDispatcher.io) {
            val userData = userDataDao.getUserData()
            if (userData != null) {
                logcat { "user death timestamp ${userData.deathTimestamp}" }
                _command.postValue(Command.ShowToast("${userData.lifeExpectancyYears}: ${
                    getFormattedDate(userData.deathTimestamp)
                }"))

            }
        }
    }

}