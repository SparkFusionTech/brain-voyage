package com.sparkfusion.quiz.brainvoyage.utils

import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.sparkfusion.quiz.brainvoyage.ui.theme.registrationTextFieldColor

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