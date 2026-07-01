package com.andef.bmicalculator.domain.usecase

import com.andef.bmicalculator.domain.model.BmiCategory
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.jupiter.api.DisplayName

private const val HEIGHT_180 = "180"
private const val HEIGHT_170 = "170"
private const val HEIGHT_200 = "200"
private const val WEIGHT_75 = "75"
private const val WEIGHT_70 = "70"
private const val WEIGHT_59 = "59"
private const val WEIGHT_74 = "74"
private const val WEIGHT_100 = "100"
private const val WEIGHT_120 = "120"
private const val BMI_23_14 = "23,14"
private const val BMI_24_22 = "24,22"
private const val BMI_18_50 = "18,50"
private const val BMI_25_00 = "25,00"
private const val BMI_30_00 = "30,00"

class CalculateBmiUseCaseTest {
    private val useCase = CalculateBmiUseCase()

    @Test
    @DisplayName("Рассчитывает ИМТ и форматирует результат с запятой")
    fun `calculates bmi and formats result with comma separator`() {
        val result = useCase(height = HEIGHT_180, weight = WEIGHT_75)

        assertEquals(BMI_23_14, result.bmi)
        assertEquals(BmiCategory.Normal, result.category)
    }

    @Test
    @DisplayName("Усекает ИМТ до двух знаков без округления")
    fun `truncates bmi to two decimals without rounding`() {
        val result = useCase(height = HEIGHT_170, weight = WEIGHT_70)

        assertEquals(BMI_24_22, result.bmi)
    }

    @Test
    @DisplayName("Определяет категорию недостаточной массы тела")
    fun `detects underweight category`() {
        val result = useCase(height = HEIGHT_180, weight = WEIGHT_59)

        assertEquals(BmiCategory.Underweight, result.category)
    }

    @Test
    @DisplayName("Определяет нижнюю границу нормальной массы тела")
    fun `detects normal category lower boundary`() {
        val result = useCase(height = HEIGHT_200, weight = WEIGHT_74)

        assertEquals(BMI_18_50, result.bmi)
        assertEquals(BmiCategory.Normal, result.category)
    }

    @Test
    @DisplayName("Определяет нижнюю границу избыточной массы тела")
    fun `detects overweight category lower boundary`() {
        val result = useCase(height = HEIGHT_200, weight = WEIGHT_100)

        assertEquals(BMI_25_00, result.bmi)
        assertEquals(BmiCategory.Overweight, result.category)
    }

    @Test
    @DisplayName("Определяет нижнюю границу ожирения")
    fun `detects obesity category lower boundary`() {
        val result = useCase(height = HEIGHT_200, weight = WEIGHT_120)

        assertEquals(BMI_30_00, result.bmi)
        assertEquals(BmiCategory.Obesity, result.category)
    }
}
