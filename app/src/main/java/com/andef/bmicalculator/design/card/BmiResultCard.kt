package com.andef.bmicalculator.design.card

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.andef.bmicalculator.design.Blue
import com.andef.bmicalculator.design.Red
import com.andef.bmicalculator.design.Yellow
import com.andef.bmicalculator.design.blackOrWhiteColor
import com.andef.bmicalculator.design.cardColors
import com.andef.bmicalculator.design.grayColor
import com.andef.bmicalculator.domain.model.BmiCategory
import com.andef.bmicalculator.domain.model.BmiResult

private const val CARD_CORNER_RADIUS_DP = 16
private const val CARD_BORDER_WIDTH_DP = 1
private const val CARD_BORDER_ALPHA = 0.3f
private const val CARD_ELEVATION_DP = 0
private const val CARD_PADDING_DP = 16
private const val CARD_CONTENT_SPACING_DP = 8
private const val RESULT_TITLE_FONT_SIZE_SP = 16
private const val RESULT_BMI_FONT_SIZE_SP = 32
private const val RESULT_BMI_LINE_HEIGHT_SP = 38
private const val RESULT_CATEGORY_FONT_SIZE_SP = 18
private const val RESULT_CATEGORY_LINE_HEIGHT_SP = 24
private const val RESULT_RECOMMENDATION_FONT_SIZE_SP = 14
private const val RESULT_RECOMMENDATION_LINE_HEIGHT_SP = 20
private const val RESULT_TITLE_TEXT = "Ваш ИМТ"

@Composable
fun BmiResultCard(isLightTheme: Boolean, result: BmiResult) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(CARD_CORNER_RADIUS_DP.dp),
        colors = cardColors(isLightTheme = isLightTheme),
        border = BorderStroke(
            width = CARD_BORDER_WIDTH_DP.dp,
            color = grayColor(isLightTheme = isLightTheme).copy(alpha = CARD_BORDER_ALPHA)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = CARD_ELEVATION_DP.dp),
    ) {
        Column(
            modifier = Modifier.padding(CARD_PADDING_DP.dp),
            verticalArrangement = Arrangement.spacedBy(CARD_CONTENT_SPACING_DP.dp),
        ) {
            Text(
                text = RESULT_TITLE_TEXT,
                color = grayColor(isLightTheme = isLightTheme),
                fontSize = RESULT_TITLE_FONT_SIZE_SP.sp,
            )
            Text(
                text = result.bmi,
                color = blackOrWhiteColor(isLightTheme = isLightTheme),
                fontSize = RESULT_BMI_FONT_SIZE_SP.sp,
                lineHeight = RESULT_BMI_LINE_HEIGHT_SP.sp,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = result.category.title,
                color = result.category.statusColor(),
                fontSize = RESULT_CATEGORY_FONT_SIZE_SP.sp,
                lineHeight = RESULT_CATEGORY_LINE_HEIGHT_SP.sp,
                fontWeight = FontWeight.Medium,
            )
            Text(
                text = result.category.recommendation,
                color = blackOrWhiteColor(isLightTheme = isLightTheme),
                fontSize = RESULT_RECOMMENDATION_FONT_SIZE_SP.sp,
                lineHeight = RESULT_RECOMMENDATION_LINE_HEIGHT_SP.sp,
            )
        }
    }
}

private fun BmiCategory.statusColor(): Color {
    return when (this) {
        BmiCategory.Underweight -> Yellow
        BmiCategory.Normal -> Blue
        BmiCategory.Overweight -> Yellow
        BmiCategory.Obesity -> Red
    }
}
