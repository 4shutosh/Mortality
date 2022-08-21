package com.planner.mortality.ui.routine

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.planner.mortality.utils.AppCoroutineDispatcher
import com.planner.mortality.utils.SingleLiveEvent
import com.planner.mortality.utils.extensions.toLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RoutineViewModel @Inject constructor(
    private val appCoroutineDispatcher: AppCoroutineDispatcher,
) : ViewModel() {

    sealed class Command {
        object ShowDatePickerDialog : Command()
    }

    private val _command = SingleLiveEvent<Command>()
    val command = _command.toLiveData()


    init {

    }

    fun actionRoutineFabClicked() {
        viewModelScope.launch(appCoroutineDispatcher.io) {
            _command.postValue(Command.ShowDatePickerDialog)
        }

    }

    fun actionTimeSetByUser(dateTimeStamp: Long) {
        viewModelScope.launch(appCoroutineDispatcher.io) {

        }

    }

}