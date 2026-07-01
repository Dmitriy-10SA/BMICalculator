package com.andef.bmicalculator.presentation

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.andef.bmicalculator.presentation.bmi.BmiScreen
import com.andef.bmicalculator.presentation.bmi.BmiViewModel

private const val BMI_ROUTE = "bmi"

@Composable
fun BmiNavHost(viewModelFactory: ViewModelProvider.Factory, isLightTheme: Boolean) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = BMI_ROUTE,
    ) {
        composable(BMI_ROUTE) {
            val viewModel: BmiViewModel = viewModel(factory = viewModelFactory)

            BmiScreen(viewModel = viewModel, isLightTheme = isLightTheme)
        }
    }
}
