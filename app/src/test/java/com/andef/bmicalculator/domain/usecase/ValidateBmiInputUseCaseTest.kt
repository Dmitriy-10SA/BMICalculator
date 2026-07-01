package com.andef.bmicalculator.domain.usecase

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.jupiter.api.DisplayName

private const val HEIGHT_VALID_COMMA = "180,25"
private const val WEIGHT_VALID_COMMA = "75,5"
private const val HEIGHT_VALID_DOT = "180.25"
private const val WEIGHT_VALID_DOT = "75.5"
private const val EMPTY_VALUE = ""
private const val HEIGHT_TOO_LOW = "49,99"
private const val HEIGHT_TOO_HIGH = "270,01"
private const val WEIGHT_TOO_LOW = "9,99"
private const val WEIGHT_TOO_HIGH = "300,01"
private const val HEIGHT_MIN = "50"
private const val HEIGHT_MAX = "270"
private const val WEIGHT_MIN = "10"
private const val WEIGHT_MAX = "300"
private const val HEIGHT_UNFINISHED_COMMA = "180,"
private const val WEIGHT_UNFINISHED_DOT = "75."
private const val HEIGHT_INVALID_TEXT = "abc"
private const val WEIGHT_VALID_INTEGER = "75"
private const val HEIGHT_EMPTY_ERROR = "Введите рост"
private const val HEIGHT_RANGE_ERROR = "Рост должен быть от 50 до 300 см"
private const val INVALID_NUMBER_ERROR = "Введите корректное значение"

class ValidateBmiInputUseCaseTest {
    private val useCase = ValidateBmiInputUseCase()

    @Test
    @DisplayName("Принимает дробный ввод с запятой")
    fun `validates comma decimal input`() {
        val validation = useCase(height = HEIGHT_VALID_COMMA, weight = WEIGHT_VALID_COMMA)

        assertTrue(validation.isValid)
        assertNull(validation.heightError)
        assertNull(validation.weightError)
    }

    @Test
    @DisplayName("Принимает дробный ввод с точкой")
    fun `validates dot decimal input`() {
        val validation = useCase(height = HEIGHT_VALID_DOT, weight = WEIGHT_VALID_DOT)

        assertTrue(validation.isValid)
        assertNull(validation.heightError)
        assertNull(validation.weightError)
    }

    @Test
    @DisplayName("Отклоняет пустые поля")
    fun `rejects empty fields`() {
        val validation = useCase(height = EMPTY_VALUE, weight = EMPTY_VALUE)

        assertFalse(validation.isValid)
        assertEquals(HEIGHT_EMPTY_ERROR, validation.heightError)
    }

    @Test
    @DisplayName("Отклоняет рост вне допустимого диапазона")
    fun `rejects height outside allowed range`() {
        val low = useCase(height = HEIGHT_TOO_LOW, weight = WEIGHT_VALID_INTEGER)
        val high = useCase(height = HEIGHT_TOO_HIGH, weight = WEIGHT_VALID_INTEGER)

        assertFalse(low.isValid)
        assertFalse(high.isValid)
        assertEquals(HEIGHT_RANGE_ERROR, low.heightError)
        assertEquals(HEIGHT_RANGE_ERROR, high.heightError)
    }

    @Test
    @DisplayName("Отклоняет вес вне допустимого диапазона")
    fun `rejects weight outside allowed range`() {
        val low = useCase(height = HEIGHT_VALID_COMMA, weight = WEIGHT_TOO_LOW)
        val high = useCase(height = HEIGHT_VALID_COMMA, weight = WEIGHT_TOO_HIGH)

        assertFalse(low.isValid)
        assertFalse(high.isValid)
    }

    @Test
    @DisplayName("Принимает включительные границы диапазонов")
    fun `accepts inclusive range boundaries`() {
        val min = useCase(height = HEIGHT_MIN, weight = WEIGHT_MIN)
        val max = useCase(height = HEIGHT_MAX, weight = WEIGHT_MAX)

        assertTrue(min.isValid)
        assertTrue(max.isValid)
    }

    @Test
    @DisplayName("Отклоняет незавершенный дробный ввод")
    fun `rejects unfinished decimal input`() {
        val comma = useCase(height = HEIGHT_UNFINISHED_COMMA, weight = WEIGHT_VALID_INTEGER)
        val dot = useCase(height = HEIGHT_VALID_COMMA, weight = WEIGHT_UNFINISHED_DOT)

        assertFalse(comma.isValid)
        assertFalse(dot.isValid)
        assertEquals(INVALID_NUMBER_ERROR, comma.heightError)
        assertEquals(INVALID_NUMBER_ERROR, dot.weightError)
    }

    @Test
    @DisplayName("Отклоняет нечисловой ввод")
    fun `rejects non numeric input`() {
        val validation = useCase(height = HEIGHT_INVALID_TEXT, weight = WEIGHT_VALID_INTEGER)

        assertFalse(validation.isValid)
        assertEquals(INVALID_NUMBER_ERROR, validation.heightError)
    }
}
