package com.planner.mortality

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.planner.mortality.data.dao.UserDataDao
import com.planner.mortality.utils.AppCoroutineDispatcher
import com.planner.mortality.utils.extensions.toLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val appCoroutineDispatcher: AppCoroutineDispatcher,
    private val userDataDao: UserDataDao,
) : ViewModel() {

    data class OnBoardingViewState(
        var isReady: Boolean = false,
        var isUserSetupDone: Boolean = false,
    )

    val onBoardingViewState: MutableLiveData<OnBoardingViewState> = MutableLiveData(
        OnBoardingViewState())

    private fun currentOnBoardingViewState(): OnBoardingViewState = onBoardingViewState.value!!

    init {
        checkForUserSetup()
    }

    private fun checkForUserSetup() {
        viewModelScope.launch(appCoroutineDispatcher.io) {
            val userData = userDataDao.getUserData()
            onBoardingViewState.postValue(
                currentOnBoardingViewState().copy(isReady = true,
                    isUserSetupDone = (userData != null && userData.dateOfBirthTimeStamp != 0L))
            )
        }
    }


}