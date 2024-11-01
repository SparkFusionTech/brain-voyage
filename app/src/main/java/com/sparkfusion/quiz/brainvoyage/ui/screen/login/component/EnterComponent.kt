package com.sparkfusion.quiz.brainvoyage.ui.screen.login.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.sparkfusion.quiz.brainvoyage.R
import com.sparkfusion.quiz.brainvoyage.ui.widget.SFProRoundedText
import com.sparkfusion.quiz.brainvoyage.utils.getLoginTextFieldContainerColors

@Composable
fun EnterComponent(
    email: String,
    emailValueChanged: (String) -> Unit,
    password: String,
    passwordValueChanged: (String) -> Unit
) {
    val textFieldContainerColors = getLoginTextFieldContainerColors()
    var showPassword by remember { mutableStateOf(false) }

    TextField(
        modifier = Modifier
            .padding(start = 24.dp, end = 24.dp, top = 40.dp)
            .fillMaxWidth(),
        colors = textFieldContainerColors,
        value = email,
        onValueChange = emailValueChanged,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.envelope_icon),
                contentDescription = stringResource(id = R.string.login_email_leading_icon_description),
                tint = MaterialTheme.colorScheme.primary
            )
        },
        placeholder = {
            SFProRoundedText(content = stringResource(id = R.string.email))
        }
    )

    TextField(
        modifier = Modifier
            .padding(start = 24.dp, end = 24.dp, top = 6.dp)
            .fillMaxWidth(),
        value = password,
        onValueChange = passwordValueChanged,
        colors = textFieldContainerColors,
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.key_icon),
                contentDescription = stringResource(id = R.string.login_password_leading_icon_description),
                tint = MaterialTheme.colorScheme.primary
            )
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        visualTransformation = if (!showPassword) PasswordVisualTransformation() else VisualTransformation.None,
        trailingIcon = {
            IconButton(onClick = { showPassword = !showPassword }) {
                Icon(
                    painter = painterResource(
                        id = if (showPassword) R.drawable.enabled_eye_icon
                        else R.drawable.disabled_eye_icon
                    ),
                    contentDescription = stringResource(id = R.string.login_password_trailing_icon_description)
                )
            }
        },
        placeholder = {
            SFProRoundedText(content = stringResource(id = R.string.password))
        }
    )
}