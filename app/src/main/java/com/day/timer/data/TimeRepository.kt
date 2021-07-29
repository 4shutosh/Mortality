package com.day.timer.data

import com.day.timer.utils.GeneralUtils.orDef
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

interface TimeRepository {
    suspend fun getDeathTime(): Long
    suspend fun setDeathTime(timeInMilliSeconds: Long)
}

class TimeRepositoryImpl @Inject constructor(
    private val preferenceRepository: PreferenceRepository
) : TimeRepository {

    override suspend fun getDeathTime(): Long {
        return preferenceRepository.getUserDeathTime().firstOrNull().orDef()
    }

    override suspend fun setDeathTime(timeInMilliSeconds: Long) {
        preferenceRepository.setUserDeathTime(timeInMilliSeconds)
    }

}