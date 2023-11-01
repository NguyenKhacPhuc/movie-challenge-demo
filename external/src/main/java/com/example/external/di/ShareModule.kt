package com.example.external.di

import android.content.Context
import com.example.external.shared.PreferenceStorage
import com.example.external.shared.SharedPreferenceStorage
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class SharedModule {
    @Singleton
    @Provides
    fun providesPreferenceStorage(context: Context): PreferenceStorage =
        SharedPreferenceStorage(context)
}