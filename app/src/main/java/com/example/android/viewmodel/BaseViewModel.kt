package com.example.android.viewmodel

import androidx.lifecycle.ViewModel

abstract class BaseViewModel<S : Any> : ViewModel() {

    val store by lazy {
        ViewStateStore(this.initState())
    }
    val currentState: S
        get() = store.state

    abstract fun initState(): S

}
