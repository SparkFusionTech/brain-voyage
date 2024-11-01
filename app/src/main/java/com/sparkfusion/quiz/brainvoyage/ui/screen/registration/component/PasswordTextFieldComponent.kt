package com.sparkfusion.quiz.brainvoyage.ui.screen.registration.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.sparkfusion.quiz.brainvoyage.R

@Composable
fun PasswordTextFieldComponent(
    password: String,
    showPassword: Boolean,
    onTextFieldChange: (String) -> Unit,
    onIconButtonClick: () -> Unit
) {
    RegistrationTextField(
        title = stringResource(id = R.string.password),
        textFieldValue = password,
        textFieldValueChanged = onTextFieldChange,
        keyboardType = KeyboardType.Password,
        transformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            IconButton(
                modifier = Modifier.padding(end = 4.dp),
                onClick = onIconButtonClick
            ) {
                Icon(
                    painter = painterResource(
                        id = if (showPassword) R.drawable.enabled_eye_icon
                        else R.drawable.disabled_eye_icon
                    ),
                    contentDescription = stringResource(id = R.string.registration_leading_icon_of_password_text_field_description)
                )
            }
        }
    )
}
