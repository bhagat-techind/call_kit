package com.techind.call_kit

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
}