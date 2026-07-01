package com.andef.bmicalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import com.andef.bmicalculator.design.BMICalculatorTheme
import com.andef.bmicalculator.presentation.BmiNavHost

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val viewModelFactory = (application as BmiCalculatorApplication)
            .appComponent
            .bmiViewModelFactory()

        setContent {
            val isLightTheme = !isSystemInDarkTheme()

            BMICalculatorTheme(isLightTheme = isLightTheme) {
                BmiNavHost(
                    viewModelFactory = viewModelFactory,
                    isLightTheme = isLightTheme
                )
            }
        }
    }
}
