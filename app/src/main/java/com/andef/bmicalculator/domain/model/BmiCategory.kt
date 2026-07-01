package com.andef.bmicalculator.domain.model

private const val UNDERWEIGHT_TITLE = "Недостаточная масса тела"
private const val UNDERWEIGHT_RECOMMENDATION =
    "Рекомендуется проконсультироваться со специалистом и обратить внимание на рацион."
private const val NORMAL_TITLE = "Нормальная масса тела"
private const val NORMAL_RECOMMENDATION = "Поддерживайте текущий образ жизни."
private const val OVERWEIGHT_TITLE = "Избыточная масса тела"
private const val OVERWEIGHT_RECOMMENDATION =
    "Рекомендуется увеличить физическую активность и скорректировать питание."
private const val OBESITY_TITLE = "Ожирение"
private const val OBESITY_RECOMMENDATION = "Рекомендуется обратиться к врачу."

enum class BmiCategory(
    val title: String,
    val recommendation: String,
) {
    Underweight(
        title = UNDERWEIGHT_TITLE,
        recommendation = UNDERWEIGHT_RECOMMENDATION,
    ),
    Normal(
        title = NORMAL_TITLE,
        recommendation = NORMAL_RECOMMENDATION,
    ),
    Overweight(
        title = OVERWEIGHT_TITLE,
        recommendation = OVERWEIGHT_RECOMMENDATION,
    ),
    Obesity(
        title = OBESITY_TITLE,
        recommendation = OBESITY_RECOMMENDATION,
    );
}
