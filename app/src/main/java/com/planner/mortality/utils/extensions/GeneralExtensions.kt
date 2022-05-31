package com.planner.mortality.utils.extensions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData


fun <T> MutableLiveData<T>.toLiveData(): LiveData<T> = this