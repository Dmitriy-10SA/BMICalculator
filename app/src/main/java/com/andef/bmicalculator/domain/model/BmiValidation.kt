package com.andef.bmicalculator.domain.model

data class BmiValidation(
    val heightError: String? = null,
    val weightError: String? = null,
) {
    val isValid: Boolean = heightError == null && weightError == null
}
