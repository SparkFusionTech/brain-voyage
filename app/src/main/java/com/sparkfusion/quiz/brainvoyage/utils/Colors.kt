package com.sparkfusion.quiz.brainvoyage.utils

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.sparkfusion.quiz.brainvoyage.ui.theme.registrationTextFieldColor

@Composable
fun descriptionColor(): Color {
    return when {
        isSystemInDarkTheme() -> MaterialTheme.colorScheme.outline
        else -> Color.Gray
    }
}

@Composable
fun fieldBorderColor(
    isFocused: Boolean,
    isDarkTheme: Boolean
): Color {
    return when {
        isFocused -> MaterialTheme.colorScheme.primary
        isDarkTheme -> TextFieldDefaults.colors().unfocusedContainerColor
        else -> Color.Black
    }
}

@Composable
fun primaryGradientWithAlpha(): Brush {
    return Brush.linearGradient(listOf(MaterialTheme.colorScheme.primary, MaterialTheme.colorScheme.primary))
}

@Composable
fun getLoginTextFieldContainerColors(): TextFieldColors {
    return TextFieldDefaults.colors(
        focusedContainerColor = Color.Transparent,
        unfocusedContainerColor = Color.Transparent,
        disabledContainerColor = Color.Transparent,
        errorContainerColor = Color.Transparent
    )
}

@Composable
fun getRegistrationTextFieldContainerColors(): TextFieldColors {
    return TextFieldDefaults.colors(
        unfocusedContainerColor = registrationTextFieldColor,
        focusedContainerColor = registrationTextFieldColor,
        errorContainerColor = registrationTextFieldColor,
        disabledContainerColor = registrationTextFieldColor,
        focusedIndicatorColor = registrationTextFieldColor,
        unfocusedIndicatorColor = registrationTextFieldColor,
        errorIndicatorColor = registrationTextFieldColor,
        disabledIndicatorColor = registrationTextFieldColor
    )
}

@Composable
fun getClearTextFieldContainerColors(): TextFieldColors {
    return TextFieldDefaults.colors(
        unfocusedContainerColor = MaterialTheme.colorScheme.background,
        focusedContainerColor = MaterialTheme.colorScheme.background,
        errorContainerColor = MaterialTheme.colorScheme.background,
        disabledContainerColor = MaterialTheme.colorScheme.background,
        focusedIndicatorColor = MaterialTheme.colorScheme.background,
        unfocusedIndicatorColor = MaterialTheme.colorScheme.background,
        errorIndicatorColor = MaterialTheme.colorScheme.background,
        disabledIndicatorColor = MaterialTheme.colorScheme.background
    )
}
