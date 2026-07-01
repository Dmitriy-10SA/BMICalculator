package com.andef.bmicalculator.presentation.bmi

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.andef.bmicalculator.R
import com.andef.bmicalculator.design.blackOrWhiteColor
import com.andef.bmicalculator.design.button.BmiDownButton
import com.andef.bmicalculator.design.card.BmiResultCard
import com.andef.bmicalculator.design.darkGrayOrWhiteColor
import com.andef.bmicalculator.design.text.field.BmiTextField
import com.andef.bmicalculator.design.topBarColors

private const val MAIN_CONTENT_WEIGHT = 1f
private const val TOP_BAR_TITLE_FONT_SIZE_SP = 22
private const val SCREEN_HORIZONTAL_PADDING_DP = 12
private const val TOP_CONTENT_SPACER_HEIGHT_DP = 6
private const val BETWEEN_FIELDS_SPACER_HEIGHT_DP = 16
private const val RESULT_TOP_SPACER_HEIGHT_DP = 16
private const val BOTTOM_CONTENT_SPACER_HEIGHT_DP = 6
private const val SCREEN_TITLE_TEXT = "Калькулятор ИМТ"
private const val HEIGHT_PLACEHOLDER_TEXT = "Рост, см"
private const val WEIGHT_PLACEHOLDER_TEXT = "Вес, кг"
private const val HEIGHT_CONTENT_DESCRIPTION = "Рост"
private const val WEIGHT_CONTENT_DESCRIPTION = "Вес"
private const val CALCULATE_BUTTON_TEXT = "Рассчитать"

@Composable
fun BmiScreen(viewModel: BmiViewModel, isLightTheme: Boolean) {
    val state by viewModel.state.collectAsState()

    BmiScreenContent(
        isLightTheme = isLightTheme,
        state = state,
        onIntent = viewModel::onIntent,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BmiScreenContent(
    isLightTheme: Boolean,
    state: BmiUiState,
    onIntent: (BmiIntent) -> Unit,
) {
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val backgroundColor = darkGrayOrWhiteColor(isLightTheme = isLightTheme)

    Scaffold(
        containerColor = backgroundColor,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = SCREEN_TITLE_TEXT,
                        color = blackOrWhiteColor(isLightTheme = isLightTheme),
                        fontSize = TOP_BAR_TITLE_FONT_SIZE_SP.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = topBarColors(isLightTheme = isLightTheme)
            )
        }
    ) { topBarPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor)
                .padding(top = topBarPadding.calculateTopPadding())
                .navigationBarsPadding()
                .imePadding()
        ) {
            MainContent(
                isLightTheme = isLightTheme,
                scrollState = rememberScrollState(),
                state = state,
                focusManager = focusManager,
                keyboardController = keyboardController,
                onIntent = onIntent
            )
            BmiDownButton(
                isLightTheme = isLightTheme,
                enabled = state.isCalculateEnabled,
                text = CALCULATE_BUTTON_TEXT,
                onSaveClick = {
                    keyboardController?.hide()
                    focusManager.clearFocus()
                    onIntent(BmiIntent.CalculateClicked)
                }
            )
        }
    }
}

@Composable
private fun ColumnScope.MainContent(
    isLightTheme: Boolean,
    scrollState: ScrollState,
    state: BmiUiState,
    focusManager: FocusManager,
    keyboardController: SoftwareKeyboardController?,
    onIntent: (BmiIntent) -> Unit,
) {
    Column(
        modifier = Modifier
            .weight(MAIN_CONTENT_WEIGHT)
            .padding(horizontal = SCREEN_HORIZONTAL_PADDING_DP.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(TOP_CONTENT_SPACER_HEIGHT_DP.dp))
        BmiTextField(
            isLightTheme = isLightTheme,
            value = state.height,
            placeholderText = HEIGHT_PLACEHOLDER_TEXT,
            leadingIcon = painterResource(id = R.drawable.height_icon),
            contentDescription = HEIGHT_CONTENT_DESCRIPTION,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = {
                    focusManager.moveFocus(FocusDirection.Down)
                }
            ),
            onValueChange = {
                onIntent(BmiIntent.HeightChanged(it))
            }
        )
        Spacer(modifier = Modifier.height(BETWEEN_FIELDS_SPACER_HEIGHT_DP.dp))
        BmiTextField(
            isLightTheme = isLightTheme,
            value = state.weight,
            placeholderText = WEIGHT_PLACEHOLDER_TEXT,
            leadingIcon = painterResource(id = R.drawable.width_icon),
            contentDescription = WEIGHT_CONTENT_DESCRIPTION,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    keyboardController?.hide()
                    focusManager.clearFocus()
                    if (state.isCalculateEnabled) onIntent(BmiIntent.CalculateClicked)
                }
            ),
            onValueChange = {
                onIntent(BmiIntent.WeightChanged(it))
            }
        )
        state.result?.let { result ->
            Spacer(modifier = Modifier.height(RESULT_TOP_SPACER_HEIGHT_DP.dp))
            BmiResultCard(isLightTheme = isLightTheme, result = result)
        }
        Spacer(modifier = Modifier.height(BOTTOM_CONTENT_SPACER_HEIGHT_DP.dp))
    }
}
