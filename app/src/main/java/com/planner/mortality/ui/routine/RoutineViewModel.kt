package com.planner.mortality.ui.routine

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.planner.mortality.data.dao.RoutineDao
import com.planner.mortality.data.entities.RoutineEntity
import com.planner.mortality.utils.AppCoroutineDispatcher
import com.planner.mortality.utils.SingleLiveEvent
import com.planner.mortality.utils.extensions.toLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RoutineViewModel @Inject constructor(
    private val appCoroutineDispatcher: AppCoroutineDispatcher,
    private val routineDao: RoutineDao,
) : ViewModel() {

    sealed class Command {
        object ShowDatePickerDialog : Command()
        object ShowSetTitleDialog : Command()
        class EnableSetTitleButton(val enable: Boolean) : Command()
        object DismissDialog : Command()
    }

    private val _command = SingleLiveEvent<Command>()
    val command = _command.toLiveData()

    private val _routines = MutableLiveData<List<RoutineAdapter.RoutineViewState>>()
    val routines = _routines.toLiveData()

    private var routineToAddTimeStamp: Long = -1L

    init {
        collectAllRoutines()
    }

    private fun collectAllRoutines() {
        viewModelScope.launch(appCoroutineDispatcher.io) {
            routineDao.getAllRoutines().map { list ->
                list.map {
                    it.toViewState()
                }
            }.collect {
                _routines.postValue(it)
            }

        }
    }

    fun actionRoutineFabClicked() {
        viewModelScope.launch(appCoroutineDispatcher.io) {
            _command.postValue(Command.ShowDatePickerDialog)
        }
    }

    fun actionTimeSetByUser(dateTimeStamp: Long) {
        viewModelScope.launch(appCoroutineDispatcher.io) {
            routineToAddTimeStamp = dateTimeStamp
            _command.postValue(Command.ShowSetTitleDialog)
        }
    }

    fun validateInputText(string: String) {
        viewModelScope.launch(appCoroutineDispatcher.io) {
            _command.postValue(Command.EnableSetTitleButton(string.isNotEmpty()))
        }
    }

    fun actionCreateTimer(title: String) {
        viewModelScope.launch(appCoroutineDispatcher.io) {
            routineDao.insert(RoutineEntity(
                title = title,
                description = "Temp Desc",
                endTimeStamp = routineToAddTimeStamp
            ))
            _command.postValue(Command.DismissDialog)
        }
    }

}