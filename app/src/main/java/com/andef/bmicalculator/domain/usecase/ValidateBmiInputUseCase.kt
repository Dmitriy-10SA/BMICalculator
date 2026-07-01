package com.andef.bmicalculator.domain.usecase

import com.andef.bmicalculator.domain.model.BmiValidation
import java.math.BigDecimal
import javax.inject.Inject

private const val HEIGHT_EMPTY_ERROR = "Введите рост"
private const val HEIGHT_RANGE_ERROR = "Рост должен быть от 50 до 300 см"
private const val WEIGHT_EMPTY_ERROR = "Введите вес"
private const val WEIGHT_RANGE_ERROR = "Вес должен быть от 10 до 500 кг"
private const val INVALID_NUMBER_ERROR = "Введите корректное значение"
private const val HEIGHT_MIN_VALUE = "50"
private const val HEIGHT_MAX_VALUE = "270"
private const val WEIGHT_MIN_VALUE = "10"
private const val WEIGHT_MAX_VALUE = "300"
private const val COMMA_SEPARATOR = ','
private const val DOT_SEPARATOR = '.'

class ValidateBmiInputUseCase @Inject constructor() {
    operator fun invoke(height: String, weight: String): BmiValidation {
        return BmiValidation(
            heightError = validateNumber(
                value = height,
                emptyMessage = HEIGHT_EMPTY_ERROR,
                rangeMessage = HEIGHT_RANGE_ERROR,
                min = BigDecimal(HEIGHT_MIN_VALUE),
                max = BigDecimal(HEIGHT_MAX_VALUE),
            ),
            weightError = validateNumber(
                value = weight,
                emptyMessage = WEIGHT_EMPTY_ERROR,
                rangeMessage = WEIGHT_RANGE_ERROR,
                min = BigDecimal(WEIGHT_MIN_VALUE),
                max = BigDecimal(WEIGHT_MAX_VALUE),
            ),
        )
    }

    private fun validateNumber(
        value: String,
        emptyMessage: String,
        rangeMessage: String,
        min: BigDecimal,
        max: BigDecimal,
    ): String? {
        if (value.isBlank()) return emptyMessage
        if (value.endsWith(COMMA_SEPARATOR) || value.endsWith(DOT_SEPARATOR)) return INVALID_NUMBER_ERROR
        val number = value.toBigDecimalOrNull() ?: return INVALID_NUMBER_ERROR
        return if (number !in min..max) rangeMessage else null
    }

    private fun String.toBigDecimalOrNull(): BigDecimal? {
        return try {
            BigDecimal(replace(COMMA_SEPARATOR, DOT_SEPARATOR))
        } catch (_: NumberFormatException) {
            null
        }
    }
}
