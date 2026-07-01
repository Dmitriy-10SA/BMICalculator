package com.andef.bmicalculator.presentation.bmi

import androidx.lifecycle.ViewModel
import com.andef.bmicalculator.domain.usecase.CalculateBmiUseCase
import com.andef.bmicalculator.domain.usecase.ValidateBmiInputUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

private const val DECIMAL_INPUT_REGEX = "^\\d{1,3}([,.]\\d{0,2})?$"
private const val COMMA_SEPARATOR = ','
private const val DOT_SEPARATOR = '.'

class BmiViewModel @Inject constructor(
    private val validateBmiInputUseCase: ValidateBmiInputUseCase,
    private val calculateBmiUseCase: CalculateBmiUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(BmiUiState())
    val state: StateFlow<BmiUiState> = _state.asStateFlow()

    fun onIntent(intent: BmiIntent) {
        when (intent) {
            is BmiIntent.HeightChanged -> updateHeight(intent.value)
            is BmiIntent.WeightChanged -> updateWeight(intent.value)
            BmiIntent.CalculateClicked -> calculate()
        }
    }

    private fun updateHeight(value: String) {
        if (!value.isAllowedDecimalInput()) return
        _state.value = _state.value.copy(height = value.normalizeDecimalSeparator()).validated()
    }

    private fun updateWeight(value: String) {
        if (!value.isAllowedDecimalInput()) return
        _state.value = _state.value.copy(weight = value.normalizeDecimalSeparator()).validated()
    }

    private fun calculate() {
        val current = _state.value.validated()
        if (!current.isCalculateEnabled) {
            _state.value = current
            return
        }
        _state.value = current.copy(
            result = calculateBmiUseCase(current.height, current.weight),
        )
    }

    private fun BmiUiState.validated(): BmiUiState {
        val validation = validateBmiInputUseCase(height, weight)
        return copy(
            heightError = validation.heightError.takeIf { height.isNotBlank() },
            weightError = validation.weightError.takeIf { weight.isNotBlank() },
        )
    }

    private fun String.isAllowedDecimalInput(): Boolean {
        if (isEmpty()) return true
        return matches(Regex(DECIMAL_INPUT_REGEX))
    }

    private fun String.normalizeDecimalSeparator(): String = replace(DOT_SEPARATOR, COMMA_SEPARATOR)
}
