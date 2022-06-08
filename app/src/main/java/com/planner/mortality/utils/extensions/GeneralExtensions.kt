package com.planner.mortality.utils.extensions

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData


fun <T> MutableLiveData<T>.toLiveData(): LiveData<T> = this

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}