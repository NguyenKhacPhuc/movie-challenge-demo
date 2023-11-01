package com.example.external.shared

import android.content.Context
import android.content.SharedPreferences
import androidx.annotation.WorkerThread
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.edit
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Storage for app and user preferences.
 */
interface PreferenceStorage {
    fun storeVisitTime(storedTime: String)
    fun getStoredVisitTime(): String
    fun clearData()
//    var ozToken: String?
}

/**
 * [PreferenceStorage] impl backed by [android.content.SharedPreferences].
 */
@Singleton
class SharedPreferenceStorage @Inject constructor(context: Context) : PreferenceStorage {

    private val prefs: Lazy<SharedPreferences> = lazy {
        context.applicationContext.getSharedPreferences(
            PREFS_NAME, Context.MODE_PRIVATE
        )
    }

    override fun storeVisitTime(storedTime: String) {
        prefs.value.edit {
            this.putString(VISIT_TIME, storedTime).commit()
        }
    }

    override fun getStoredVisitTime(): String {
        return prefs.value.getString(VISIT_TIME, "") ?: ""
    }

    override fun clearData() {
        prefs.value.edit { clear().commit() }
    }

    companion object {
        const val PREFS_NAME = "demo"
        const val VISIT_TIME = "VISIT_TIME"
    }
}
