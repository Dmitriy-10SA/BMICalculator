package com.andef.bmicalculator.design.button

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.andef.bmicalculator.design.buttonColors

private const val BUTTON_CORNER_RADIUS_DP = 16
private const val BUTTON_TEXT_MAX_LINES = 1
private const val BUTTON_TEXT_VERTICAL_PADDING_DP = 11
private const val BUTTON_TEXT_FONT_SIZE_SP = 16

@Composable
fun BmiButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    Button(
        modifier = modifier,
        onClick = onClick,
        enabled = enabled,
        shape = RoundedCornerShape(BUTTON_CORNER_RADIUS_DP.dp),
        colors = buttonColors()
    ) {
        Text(
            maxLines = BUTTON_TEXT_MAX_LINES,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(vertical = BUTTON_TEXT_VERTICAL_PADDING_DP.dp),
            text = text,
            textAlign = TextAlign.Center,
            fontSize = BUTTON_TEXT_FONT_SIZE_SP.sp
        )
    }
}
