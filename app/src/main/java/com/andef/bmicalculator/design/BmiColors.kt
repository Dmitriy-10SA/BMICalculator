package com.andef.bmicalculator.design

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.tween
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private const val BLUE_COLOR_VALUE = 0xFF00ACFF
private const val YELLOW_COLOR_VALUE = 0xFFFFB800
private const val RED_COLOR_VALUE = 0xFFFF4848
private const val WHITE_COLOR_VALUE = 0xFFFFFFFF
private const val BLACK_COLOR_VALUE = 0xFF000000
private const val DARK_GRAY_COLOR_VALUE = 0xFF121212
private const val GRAY_FOR_LIGHT_COLOR_VALUE = 0xFF8B8F92
private const val GRAY_FOR_DARK_COLOR_VALUE = 0xFFB6BABD
private const val DEFAULT_ANIMATION_DURATION_MS = 800
private const val TOP_BAR_ANIMATION_DURATION_MS = 260
private const val DISABLED_ALPHA = 0.3f
private const val SELECTION_BACKGROUND_ALPHA = 0.2f

val Blue = Color(BLUE_COLOR_VALUE)
val Yellow = Color(YELLOW_COLOR_VALUE)
val Red = Color(RED_COLOR_VALUE)
val White = Color(WHITE_COLOR_VALUE)
val Black = Color(BLACK_COLOR_VALUE)
val DarkGray = Color(DARK_GRAY_COLOR_VALUE)
val GrayForLight = Color(GRAY_FOR_LIGHT_COLOR_VALUE)
val GrayForDark = Color(GRAY_FOR_DARK_COLOR_VALUE)

private fun anim(duration: Int): TweenSpec<Color> =
    tween(durationMillis = duration, easing = FastOutSlowInEasing)

@Composable
fun grayColor(isLightTheme: Boolean, duration: Int = DEFAULT_ANIMATION_DURATION_MS) =
    animateColorAsState(
        targetValue = if (isLightTheme) GrayForLight else GrayForDark,
        animationSpec = anim(duration)
    ).value

@Composable
fun blackOrWhiteColor(isLightTheme: Boolean, duration: Int = DEFAULT_ANIMATION_DURATION_MS) =
    animateColorAsState(
        targetValue = if (isLightTheme) Black else White,
        animationSpec = anim(duration)
    ).value

@Composable
fun darkGrayOrWhiteColor(isLightTheme: Boolean, duration: Int = DEFAULT_ANIMATION_DURATION_MS) =
    animateColorAsState(
        targetValue = if (isLightTheme) White else DarkGray,
        animationSpec = anim(duration)
    ).value

@Composable
fun buttonColors() = ButtonDefaults.buttonColors(
    containerColor = Blue,
    contentColor = White,
    disabledContainerColor = Blue.copy(alpha = DISABLED_ALPHA),
    disabledContentColor = White
)

@Composable
fun cardColors(isLightTheme: Boolean) = CardDefaults.cardColors(
    containerColor = darkGrayOrWhiteColor(isLightTheme = isLightTheme),
    contentColor = blackOrWhiteColor(isLightTheme = isLightTheme),
    disabledContainerColor = darkGrayOrWhiteColor(isLightTheme = isLightTheme),
    disabledContentColor = blackOrWhiteColor(isLightTheme = isLightTheme)
)

@Composable
fun textFieldColors(value: String, isLightTheme: Boolean) = OutlinedTextFieldDefaults.colors(
    focusedTextColor = blackOrWhiteColor(isLightTheme = isLightTheme),
    focusedContainerColor = darkGrayOrWhiteColor(isLightTheme = isLightTheme),
    focusedLabelColor = blackOrWhiteColor(isLightTheme = isLightTheme),
    focusedPlaceholderColor = grayColor(isLightTheme = isLightTheme),
    focusedLeadingIconColor = when (value.isEmpty()) {
        true -> grayColor(isLightTheme = isLightTheme)
        else -> blackOrWhiteColor(isLightTheme = isLightTheme)
    },
    focusedTrailingIconColor = when (value.isEmpty()) {
        true -> grayColor(isLightTheme = isLightTheme)
        else -> blackOrWhiteColor(isLightTheme = isLightTheme)
    },
    unfocusedTrailingIconColor = when (value.isEmpty()) {
        true -> grayColor(isLightTheme = isLightTheme)
        else -> blackOrWhiteColor(isLightTheme = isLightTheme)
    },
    focusedBorderColor = grayColor(isLightTheme = isLightTheme),
    unfocusedTextColor = blackOrWhiteColor(isLightTheme = isLightTheme),
    unfocusedContainerColor = darkGrayOrWhiteColor(isLightTheme = isLightTheme),
    unfocusedLabelColor = blackOrWhiteColor(isLightTheme = isLightTheme),
    unfocusedPlaceholderColor = grayColor(isLightTheme = isLightTheme),
    unfocusedLeadingIconColor = when (value.isEmpty()) {
        true -> grayColor(isLightTheme = isLightTheme)
        else -> blackOrWhiteColor(isLightTheme = isLightTheme)
    },
    cursorColor = blackOrWhiteColor(isLightTheme = isLightTheme),
    errorBorderColor = Red,
    errorTextColor = blackOrWhiteColor(isLightTheme = isLightTheme),
    errorPlaceholderColor = grayColor(isLightTheme = isLightTheme),
    errorLeadingIconColor = when (value.isEmpty()) {
        true -> grayColor(isLightTheme = isLightTheme)
        else -> blackOrWhiteColor(isLightTheme = isLightTheme)
    },
    unfocusedBorderColor = grayColor(isLightTheme = isLightTheme),
    selectionColors = TextSelectionColors(
        handleColor = Blue,
        backgroundColor = Blue.copy(alpha = SELECTION_BACKGROUND_ALPHA)
    )
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun topBarColors(isLightTheme: Boolean) = TopAppBarDefaults.topAppBarColors(
    containerColor = darkGrayOrWhiteColor(
        isLightTheme = isLightTheme,
        duration = TOP_BAR_ANIMATION_DURATION_MS
    ),
    scrolledContainerColor = darkGrayOrWhiteColor(
        isLightTheme = isLightTheme,
        duration = TOP_BAR_ANIMATION_DURATION_MS
    ),
    navigationIconContentColor = blackOrWhiteColor(
        isLightTheme = isLightTheme,
        duration = TOP_BAR_ANIMATION_DURATION_MS
    ),
    titleContentColor = blackOrWhiteColor(
        isLightTheme = isLightTheme,
        duration = TOP_BAR_ANIMATION_DURATION_MS
    ),
    actionIconContentColor = blackOrWhiteColor(
        isLightTheme = isLightTheme,
        duration = TOP_BAR_ANIMATION_DURATION_MS
    ),
)
