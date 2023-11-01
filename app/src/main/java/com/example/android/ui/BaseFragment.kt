package com.example.android.ui

import dagger.android.support.DaggerFragment

abstract class BaseFragment: DaggerFragment() {
    private var loadingFragment: LoadingFragment? = null
    abstract fun initObserveData()
    abstract fun initData()
    abstract fun initView()
    abstract fun initAction()
    fun loading() {
        if (loadingFragment?.isDetached == false) return
        if (loadingFragment == null) {
            loadingFragment = LoadingFragment.newInstance()
        }
        val transaction = childFragmentManager.beginTransaction()
        transaction.add(loadingFragment!!, LoadingFragment.TAG)
        transaction.commitAllowingStateLoss()
    }

    fun dismiss() {
        loadingFragment?.dismissAllowingStateLoss()
        loadingFragment = null
    }
}