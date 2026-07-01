package com.andef.bmicalculator.domain.usecase

import com.andef.bmicalculator.domain.model.BmiCategory
import com.andef.bmicalculator.domain.model.BmiResult
import java.math.BigDecimal
import java.math.RoundingMode
import javax.inject.Inject

private const val CENTIMETERS_IN_METER = "100"
private const val BMI_DIVISION_SCALE = 8
private const val BMI_DISPLAY_SCALE = 2
private const val UNDERWEIGHT_MAX_BMI = "18.5"
private const val NORMAL_MAX_BMI = "25"
private const val OVERWEIGHT_MAX_BMI = "30"
private const val COMMA_SEPARATOR = ','
private const val DOT_SEPARATOR = '.'

class CalculateBmiUseCase @Inject constructor() {
    operator fun invoke(height: String, weight: String): BmiResult {
        val heightMeters = height.normalizedDecimal().divide(BigDecimal(CENTIMETERS_IN_METER))
        val bmi = weight.normalizedDecimal().divide(
            heightMeters.multiply(heightMeters),
            BMI_DIVISION_SCALE,
            RoundingMode.DOWN,
        )
        val bmiText = bmi
            .setScale(BMI_DISPLAY_SCALE, RoundingMode.DOWN)
            .toPlainString()
            .replace(DOT_SEPARATOR, COMMA_SEPARATOR)
        return BmiResult(
            bmi = bmiText,
            category = bmi.toCategory(),
        )
    }

    private fun String.normalizedDecimal(): BigDecimal =
        replace(COMMA_SEPARATOR, DOT_SEPARATOR).toBigDecimal()

    private fun BigDecimal.toCategory(): BmiCategory {
        return when {
            this < BigDecimal(UNDERWEIGHT_MAX_BMI) -> BmiCategory.Underweight
            this < BigDecimal(NORMAL_MAX_BMI) -> BmiCategory.Normal
            this < BigDecimal(OVERWEIGHT_MAX_BMI) -> BmiCategory.Overweight
            else -> BmiCategory.Obesity
        }
    }
}
