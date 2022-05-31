package com.planner.mortality.ui.setup

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.planner.mortality.utils.AppCoroutineDispatcher
import com.planner.mortality.utils.SingleLiveEvent
import com.planner.mortality.utils.extensions.toLiveData
import com.planner.mortality.utils.getTimeDifferenceInYears
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import logcat.logcat
import javax.inject.Inject

@HiltViewModel
class FragmentSetupViewModel @Inject constructor(
    private val appCoroutineDispatcher: AppCoroutineDispatcher,
) : ViewModel() {

    sealed class Command {
        class MoveToPage(val index: Int = -1, val enableCta: Boolean = false) : Command()
        class EnableCta(val enableCta: Boolean = true) : Command()
        class Notify(val message: String) : Command()

        object EndSetupProcess : Command()
    }

    data class MortalityUserSetupModel(
        val dateOfBirthTimeStamp: Long? = -1L,
        val userAge: Int? = -1,
        val lifeExpectancyYears: Int = -1,
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
                    _command.postValue(Command.EndSetupProcess)
                }
                else -> Unit
            }
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

    companion object {
        internal const val PAGE_ABOUT = 0
        internal const val PAGE_BIRTH_DATE = 1
        internal const val PAGE_DEATH = 2
    }


}