package com.example.android.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class MainViewModel @Inject constructor() : ViewModel() {
    private val mutableIsLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = mutableIsLoading

    private fun startLoading() {
        mutableIsLoading.postValue(true)
    }

    private fun stopLoading() {
        mutableIsLoading.postValue(false)
    }
}