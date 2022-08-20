package com.planner.mortality.ui.setup

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.planner.mortality.data.dao.UserDataDao
import com.planner.mortality.data.entities.UserDataEntity
import com.planner.mortality.utils.AppCoroutineDispatcher
import com.planner.mortality.utils.SingleLiveEvent
import com.planner.mortality.utils.addYearsToTimeStamp
import com.planner.mortality.utils.extensions.toLiveData
import com.planner.mortality.utils.getDaySecondsFromHourMinutes
import com.planner.mortality.utils.getHoursAndMinutesFromDaySeconds
import com.planner.mortality.utils.getTimeDifferenceInYears
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import logcat.logcat
import javax.inject.Inject

@HiltViewModel
class FragmentSetupViewModel @Inject constructor(
    private val appCoroutineDispatcher: AppCoroutineDispatcher,
    private val userDataDao: UserDataDao,
) : ViewModel() {

    sealed class Command {
        class MoveToPage(val index: Int = -1, val enableCta: Boolean = false) : Command()
        class EnableCta(val enableCta: Boolean = true) : Command()
        class Notify(val message: String) : Command()

        object EndSetupProcess : Command()
    }

    data class MortalityUserSetupModel(
        val dateOfBirthTimeStamp: Long = -1L,
        val userAge: Int = -1,
        val lifeExpectancyYears: Int = -1,
        val sleepHours: Int = 8,
        val endOfDaySeconds: Int = -1,
    )

    private var setupModel = MortalityUserSetupModel()

    private val _command = SingleLiveEvent<Command>()
    val command = _command.toLiveData()

    private val _userAgeLiveData = MutableLiveData<Int>()
    val userAgeLiveData = _userAgeLiveData.toLiveData()


    private suspend fun validateAndStoreDateOfBirth(timeStamp: Long): Boolean {
        val userAge = getTimeDifferenceInYears(timeStamp)
        setupModel = setupModel.copy(dateOfBirthTimeStamp = timeStamp, userAge = userAge)
        _userAgeLiveData.postValue(userAge)
        return timeStamp > 0
    }

    fun actionCtaButtonClicked(currentPageIndex: Int) {
        viewModelScope.launch(appCoroutineDispatcher.io) {
            when (currentPageIndex) {
                PAGE_ABOUT -> {
                    _command.postValue(Command.MoveToPage())
                }
                PAGE_BIRTH_DATE -> {
                    if (setupModel.dateOfBirthTimeStamp != -1L)
                        _command.postValue(Command.MoveToPage())
                    else _command.postValue(Command.Notify("Please add your birth date"))
                }
                PAGE_DEATH -> {
                    _command.postValue(Command.MoveToPage())
                }
                PAGE_SLEEP -> {
                    _command.postValue(Command.MoveToPage())
                }
                PAGE_DAY_END -> {
                    userSetupComplete()
                }
                else -> Unit
            }
        }
    }

    private fun userSetupComplete() {
        viewModelScope.launch(appCoroutineDispatcher.io) {
            if (setupModel.dateOfBirthTimeStamp != -1L) {
                val userDeathTimeStamp = addYearsToTimeStamp(setupModel.dateOfBirthTimeStamp,
                    setupModel.lifeExpectancyYears)
                userDataDao.insert(UserDataEntity(
                    dateOfBirthTimeStamp = setupModel.dateOfBirthTimeStamp,
                    lifeExpectancyYears = setupModel.lifeExpectancyYears,
                    deathTimestamp = userDeathTimeStamp,
                    sleepHours = setupModel.sleepHours,
                    endOfDaySeconds = setupModel.endOfDaySeconds,
                ))
            }
            _command.postValue(Command.Notify("Setup Complete!"))
            delay(1500)
            _command.postValue(Command.EndSetupProcess)
        }
    }

    fun actionViewPagerPageChanged(position: Int) {
        viewModelScope.launch(appCoroutineDispatcher.io) {
            when (position) {
                PAGE_ABOUT -> {
                    _command.postValue(Command.EnableCta(true))
                }
                PAGE_BIRTH_DATE -> {
                    if (setupModel.dateOfBirthTimeStamp != -1L)
                        _command.postValue(Command.EnableCta())
                }
                PAGE_DEATH -> {
                    if (setupModel.lifeExpectancyYears != setupModel.userAge && setupModel.lifeExpectancyYears != -1)
                        _command.postValue(Command.EnableCta())
                }
                PAGE_SLEEP -> {
                    _command.postValue(Command.EnableCta())
                }
                PAGE_DAY_END -> {
                    if (setupModel.endOfDaySeconds != -1)
                        _command.postValue(Command.EnableCta())
                }
                else -> Unit
            }
        }
    }

    fun actionBirthDateSet(timeStamp: Long) {
        viewModelScope.launch(appCoroutineDispatcher.io) {
            if (validateAndStoreDateOfBirth(timeStamp)) {
                _command.postValue(Command.EnableCta())
            }
        }
    }

    fun actionLifeSpanSet(lifeSpan: Int) {
        viewModelScope.launch(appCoroutineDispatcher.io) {
            if (lifeSpan != setupModel.userAge) {
                setupModel = setupModel.copy(lifeExpectancyYears = lifeSpan)
                _command.postValue(Command.EnableCta())
            } else {
                _command.postValue(Command.EnableCta(false))
            }
        }
    }

    fun actionSleepAmountSet(sleepHours: Int) {
        viewModelScope.launch(appCoroutineDispatcher.io) {
            setupModel = setupModel.copy(sleepHours = sleepHours)
        }
    }

    fun actionUserDayEndTimeSet(hour: Int, minutes: Int) {
        viewModelScope.launch(appCoroutineDispatcher.io) {
            val daySeconds = getDaySecondsFromHourMinutes(hour, minutes)
            logcat { "daySeconds origin $hour $minutes to and fro $daySeconds" }
            setupModel = setupModel.copy(endOfDaySeconds = daySeconds)
            _command.postValue(Command.EnableCta())
        }
    }

    companion object {
        internal const val PAGE_ABOUT = 0
        internal const val PAGE_BIRTH_DATE = 1
        internal const val PAGE_DEATH = 2
        internal const val PAGE_SLEEP = 3
        internal const val PAGE_DAY_END = 4
    }


}