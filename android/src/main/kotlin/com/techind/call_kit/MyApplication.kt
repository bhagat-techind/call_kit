package com.techind.call_kit

import android.os.PowerManager
import android.util.Log
import androidx.multidex.MultiDexApplication

class MyApplication : MultiDexApplication() {
    companion object {
        @JvmStatic
        private lateinit var instance: MyApplication

        @JvmStatic
        fun getInstance(): MyApplication {
            return instance
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        Log.w("MyApplication","==>> onCreate called")
    }

    fun wakeLockRequest(duration: Int) {

        val pm = getInstance().getSystemService(POWER_SERVICE) as PowerManager
        val wakeLock = pm.newWakeLock(
            PowerManager.SCREEN_BRIGHT_WAKE_LOCK or PowerManager.FULL_WAKE_LOCK or PowerManager.ACQUIRE_CAUSES_WAKEUP,
            "Callkit:PowerManager"
        )
        wakeLock.acquire((duration + 3).toLong())
    }

}