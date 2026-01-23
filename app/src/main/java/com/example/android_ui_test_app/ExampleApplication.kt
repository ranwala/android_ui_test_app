package com.example.android_ui_test_app

import android.app.Application
import io.scanbot.sdk.ScanbotSDKInitializer

class ExampleApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        ScanbotSDKInitializer()
            // optional: uncomment the next line if you have a license key
            // .license(this, LICENSE_KEY)
            .initialize(this)
    }
}