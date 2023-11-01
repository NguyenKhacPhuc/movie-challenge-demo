package com.example.android.ui

import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import com.example.android.databinding.MainActivityBinding
import com.example.android.viewmodel.MainViewModel
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class MainActivity : BaseActivity(), HasAndroidInjector {
    private lateinit var binding: MainActivityBinding

    @Inject
    internal lateinit var androidInjector: DispatchingAndroidInjector<Any>

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
    }
    override val toolBar: Toolbar?
        get() = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        viewModel.isLoading.observe(this) {
            if (it) {
                loading()
            } else {
                dismiss()
            }
        }
    }

    override fun androidInjector(): AndroidInjector<Any> = androidInjector
}