package com.andef.bmicalculator.presentation.bmi

import com.andef.bmicalculator.domain.model.BmiResult

data class BmiUiState(
    val height: String = "",
    val weight: String = "",
    val heightError: String? = null,
    val weightError: String? = null,
    val result: BmiResult? = null,
) {
    val isCalculateEnabled: Boolean =
        height.isNotBlank() && weight.isNotBlank() && heightError == null && weightError == null
}
