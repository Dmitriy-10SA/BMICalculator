package com.andef.bmicalculator.design.button

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.andef.bmicalculator.design.blackOrWhiteColor

private const val SAVE_TEXT = "Сохранить"
private const val DIVIDER_THICKNESS_DP = 1
private const val DIVIDER_ALPHA = 0.2f
private const val BUTTON_VERTICAL_SPACER_HEIGHT_DP = 8
private const val BUTTON_HORIZONTAL_PADDING_DP = 12

@Composable
fun BmiDownButton(
    isLightTheme: Boolean,
    enabled: Boolean,
    onSaveClick: () -> Unit,
    text: String = SAVE_TEXT
) {
    Column {
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            thickness = DIVIDER_THICKNESS_DP.dp,
            color = blackOrWhiteColor(isLightTheme = isLightTheme).copy(alpha = DIVIDER_ALPHA)
        )
        Spacer(modifier = Modifier.height(BUTTON_VERTICAL_SPACER_HEIGHT_DP.dp))
        BmiButton(
            text = text,
            onClick = onSaveClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = BUTTON_HORIZONTAL_PADDING_DP.dp)
                .imePadding(),
            enabled = enabled
        )
        Spacer(modifier = Modifier.height(BUTTON_VERTICAL_SPACER_HEIGHT_DP.dp))
    }
}
