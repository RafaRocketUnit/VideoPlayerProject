package com.globant.videoplayerproject.utils

import android.app.Application

class CustomApplication : Application() {

    companion object {
        private lateinit var instance: CustomApplication
        fun get() = instance
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}


