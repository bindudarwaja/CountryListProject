package com.myapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Application class is a base class for maintaining global application state
 */

@HiltAndroidApp
class MyApplication : Application() {
}