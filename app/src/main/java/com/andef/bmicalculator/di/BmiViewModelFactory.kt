package com.andef.bmicalculator.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.andef.bmicalculator.domain.usecase.CalculateBmiUseCase
import com.andef.bmicalculator.domain.usecase.ValidateBmiInputUseCase
import com.andef.bmicalculator.presentation.bmi.BmiViewModel
import javax.inject.Inject

private const val UNKNOWN_VIEW_MODEL_CLASS_ERROR = "Unknown ViewModel class: "

class BmiViewModelFactory @Inject constructor(
    private val validateBmiInputUseCase: ValidateBmiInputUseCase,
    private val calculateBmiUseCase: CalculateBmiUseCase,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BmiViewModel::class.java)) {
            return modelClass.cast(BmiViewModel(validateBmiInputUseCase, calculateBmiUseCase))
                ?: throw IllegalArgumentException(UNKNOWN_VIEW_MODEL_CLASS_ERROR + modelClass.name)
        }
        throw IllegalArgumentException(UNKNOWN_VIEW_MODEL_CLASS_ERROR + modelClass.name)
    }
}
