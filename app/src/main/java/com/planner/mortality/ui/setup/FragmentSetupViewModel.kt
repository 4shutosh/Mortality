package com.planner.mortality.ui.setup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.planner.mortality.utils.AppCoroutineDispatcher
import com.planner.mortality.utils.SingleLiveEvent
import com.planner.mortality.utils.extensions.toLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FragmentSetupViewModel @Inject constructor(
    private val appCoroutineDispatcher: AppCoroutineDispatcher,
) : ViewModel() {

    sealed class Command {
        class MoveToPage(val index: Int = -1, val enableCta: Boolean = false) : Command()
        class EnableCta(val enableCta: Boolean = true) : Command()
        class Notify(val message: String) : Command()
    }

    data class MortalityUserSetupModel(
        val dateOfBirthTimeStamp: Long? = 0,
        val lifeExpectancyYears: Int = 75,
    )

    private var setupModel = MortalityUserSetupModel()

    private val _command = SingleLiveEvent<Command>()
    val command = _command.toLiveData()


    private suspend fun validateAndStoreDateOfBirth(timeStamp: Long): Boolean {
        setupModel = setupModel.copy(dateOfBirthTimeStamp = timeStamp)
        return timeStamp > 0
    }

    fun actionCtaButtonClicked(currentPageIndex: Int) {
        viewModelScope.launch(appCoroutineDispatcher.io) {
            when (currentPageIndex) {
                PAGE_ABOUT -> {
                    _command.postValue(Command.MoveToPage())
                }
                PAGE_BIRTH_DATE -> {
                    if (setupModel.dateOfBirthTimeStamp != 0L)
                        _command.postValue(Command.MoveToPage())
                    else _command.postValue(Command.Notify("Please add your birth date"))
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
                    if (setupModel.dateOfBirthTimeStamp != 0L)
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

    companion object {
        internal const val PAGE_ABOUT = 0
        internal const val PAGE_BIRTH_DATE = 1
        internal const val PAGE_DEATH = 2
    }


}