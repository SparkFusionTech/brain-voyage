package com.sparkfusion.quiz.brainvoyage.ui.screen.registration

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sparkfusion.quiz.brainvoyage.R
import com.sparkfusion.quiz.brainvoyage.ui.widget.SFProRoundedText
import com.sparkfusion.quiz.brainvoyage.utils.getRegistrationTextFieldContainerColors

@Composable
fun RegistrationTextField(
    transformation: VisualTransformation = VisualTransformation.None,
    keyboardType: KeyboardType,
    title: String,
    textFieldValue: String,
    textFieldValueChanged: (String) -> Unit,
    trailingIcon: @Composable () -> Unit
) {
    SFProRoundedText(
        modifier = Modifier.padding(start = 24.dp),
        content = title,
        fontSize = 18.sp,
        fontWeight = FontWeight.Medium
    )

    OutlinedTextField(
        modifier = Modifier
            .padding(start = 24.dp, end = 24.dp)
            .fillMaxWidth(),
        value = textFieldValue,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        visualTransformation = transformation,
        onValueChange = textFieldValueChanged,
        placeholder = {
            SFProRoundedText(content = stringResource(id = R.string.enter_here))
        },
        trailingIcon = trailingIcon,
        colors = getRegistrationTextFieldContainerColors(),
        shape = RoundedCornerShape(12.dp)
    )
}