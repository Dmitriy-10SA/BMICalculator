package com.andef.bmicalculator

import android.app.Application
import com.andef.bmicalculator.di.AppComponent
import com.andef.bmicalculator.di.DaggerAppComponent

class BmiCalculatorApplication : Application() {
    val appComponent: AppComponent by lazy {
        DaggerAppComponent.create()
    }
}
