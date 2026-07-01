package com.andef.bmicalculator.design.text.field

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.andef.bmicalculator.design.blackOrWhiteColor
import com.andef.bmicalculator.design.textFieldColors

private const val TEXT_FIELD_FONT_SIZE_SP = 16
private const val TEXT_FIELD_PLACEHOLDER_MAX_LINES = 1
private const val TEXT_FIELD_CORNER_RADIUS_DP = 16

@Composable
fun BmiTextField(
    isLightTheme: Boolean,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholderText: String,
    leadingIcon: Painter,
    contentDescription: String,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default
) {
    Column(modifier = modifier.fillMaxWidth()) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = value,
            onValueChange = onValueChange,
            placeholder = {
                Text(
                    text = placeholderText,
                    fontSize = TEXT_FIELD_FONT_SIZE_SP.sp,
                    maxLines = TEXT_FIELD_PLACEHOLDER_MAX_LINES,
                    overflow = TextOverflow.Ellipsis
                )
            },
            leadingIcon = { Icon(painter = leadingIcon, contentDescription = contentDescription) },
            visualTransformation = visualTransformation,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            singleLine = true,
            shape = RoundedCornerShape(TEXT_FIELD_CORNER_RADIUS_DP.dp),
            colors = textFieldColors(value = value, isLightTheme = isLightTheme),
            textStyle = TextStyle(
                color = blackOrWhiteColor(isLightTheme),
                fontSize = TEXT_FIELD_FONT_SIZE_SP.sp
            )
        )
    }
}
