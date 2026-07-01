package com.andef.bmicalculator.presentation.bmi

import com.andef.bmicalculator.domain.usecase.CalculateBmiUseCase
import com.andef.bmicalculator.domain.usecase.ValidateBmiInputUseCase
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.jupiter.api.DisplayName

private const val HEIGHT_WITH_DOT = "180.25"
private const val HEIGHT_WITH_COMMA = "180,25"
private const val WEIGHT_WITH_DOT = "75.55"
private const val WEIGHT_WITH_COMMA = "75,55"
private const val HEIGHT_VALID = "180"
private const val WEIGHT_VALID = "75"
private const val HEIGHT_INVALID_TOO_LONG = "1000"
private const val WEIGHT_INVALID_PRECISION = "75,555"
private const val HEIGHT_OUT_OF_RANGE = "40"
private const val WEIGHT_OUT_OF_RANGE = "9"
private const val HEIGHT_CHANGED = "190"
private const val WEIGHT_CHANGED = "90"
private const val EMPTY_VALUE = ""
private const val BMI_23_14 = "23,14"
private const val BMI_24_93 = "24,93"

class BmiViewModelTest {
    private fun createViewModel(): BmiViewModel {
        return BmiViewModel(
            validateBmiInputUseCase = ValidateBmiInputUseCase(),
            calculateBmiUseCase = CalculateBmiUseCase(),
        )
    }

    @Test
    @DisplayName("Нормализует точку в запятую при дробном вводе")
    fun `normalizes dot decimal separator to comma`() {
        val viewModel = createViewModel()

        viewModel.onIntent(BmiIntent.HeightChanged(HEIGHT_WITH_DOT))
        viewModel.onIntent(BmiIntent.WeightChanged(WEIGHT_WITH_DOT))

        val state = viewModel.state.value

        assertEquals(HEIGHT_WITH_COMMA, state.height)
        assertEquals(WEIGHT_WITH_COMMA, state.weight)
        assertTrue(state.isCalculateEnabled)
    }

    @Test
    @DisplayName("Игнорирует ввод, не соответствующий десятичному формату")
    fun `ignores input that does not match decimal format`() {
        val viewModel = createViewModel()

        viewModel.onIntent(BmiIntent.HeightChanged(HEIGHT_VALID))
        viewModel.onIntent(BmiIntent.HeightChanged(HEIGHT_INVALID_TOO_LONG))
        viewModel.onIntent(BmiIntent.WeightChanged(WEIGHT_VALID))
        viewModel.onIntent(BmiIntent.WeightChanged(WEIGHT_INVALID_PRECISION))

        val state = viewModel.state.value

        assertEquals(HEIGHT_VALID, state.height)
        assertEquals(WEIGHT_VALID, state.weight)
    }

    @Test
    @DisplayName("Отключает расчет при пустых полях")
    fun `disables calculation when fields are empty`() {
        val viewModel = createViewModel()

        viewModel.onIntent(BmiIntent.HeightChanged(EMPTY_VALUE))
        viewModel.onIntent(BmiIntent.WeightChanged(EMPTY_VALUE))

        assertFalse(viewModel.state.value.isCalculateEnabled)
    }

    @Test
    @DisplayName("Показывает ошибки и отключает расчет для значений вне диапазона")
    fun `shows errors and disables calculation for out of range values`() {
        val viewModel = createViewModel()

        viewModel.onIntent(BmiIntent.HeightChanged(HEIGHT_OUT_OF_RANGE))
        viewModel.onIntent(BmiIntent.WeightChanged(WEIGHT_OUT_OF_RANGE))

        val state = viewModel.state.value

        assertFalse(state.isCalculateEnabled)
        assertNotNull(state.heightError)
        assertNotNull(state.weightError)
    }

    @Test
    @DisplayName("Рассчитывает ИМТ для валидного ввода")
    fun `calculates bmi for valid input`() {
        val viewModel = createViewModel()

        viewModel.onIntent(BmiIntent.HeightChanged(HEIGHT_VALID))
        viewModel.onIntent(BmiIntent.WeightChanged(WEIGHT_VALID))
        viewModel.onIntent(BmiIntent.CalculateClicked)

        val result = viewModel.state.value.result

        assertNotNull(result)
        assertEquals(BMI_23_14, result?.bmi)
    }

    @Test
    @DisplayName("Не рассчитывает ИМТ для невалидного ввода")
    fun `does not calculate bmi for invalid input`() {
        val viewModel = createViewModel()

        viewModel.onIntent(BmiIntent.HeightChanged(HEIGHT_OUT_OF_RANGE))
        viewModel.onIntent(BmiIntent.WeightChanged(WEIGHT_VALID))
        viewModel.onIntent(BmiIntent.CalculateClicked)

        assertNull(viewModel.state.value.result)
    }

    @Test
    @DisplayName("Сохраняет предыдущий результат до следующего успешного расчета")
    fun `keeps previous result until next successful calculation`() {
        val viewModel = createViewModel()

        viewModel.onIntent(BmiIntent.HeightChanged(HEIGHT_VALID))
        viewModel.onIntent(BmiIntent.WeightChanged(WEIGHT_VALID))
        viewModel.onIntent(BmiIntent.CalculateClicked)
        viewModel.onIntent(BmiIntent.HeightChanged(HEIGHT_OUT_OF_RANGE))

        assertEquals(BMI_23_14, viewModel.state.value.result?.bmi)

        viewModel.onIntent(BmiIntent.HeightChanged(HEIGHT_CHANGED))
        viewModel.onIntent(BmiIntent.WeightChanged(WEIGHT_CHANGED))
        viewModel.onIntent(BmiIntent.CalculateClicked)

        assertEquals(BMI_24_93, viewModel.state.value.result?.bmi)
    }
}
