package com.jasonkhew96.wenku8

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class Wenku8Application : Application() {
    init {
        instance = this
    }

    companion object {
        private var instance: Wenku8Application? = null

        fun applicationContext(): Context {
            return instance!!.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()
//        val context: Context = applicationContext
    }
}
