package com.andef.bmicalculator.di

import dagger.Component
import javax.inject.Singleton

@Singleton
@Component
interface AppComponent {
    fun bmiViewModelFactory(): BmiViewModelFactory
}