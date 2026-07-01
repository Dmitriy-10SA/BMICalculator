package com.andef.bmicalculator.presentation.bmi

sealed interface BmiIntent {
    data class HeightChanged(val value: String) : BmiIntent
    data class WeightChanged(val value: String) : BmiIntent
    data object CalculateClicked : BmiIntent
}
