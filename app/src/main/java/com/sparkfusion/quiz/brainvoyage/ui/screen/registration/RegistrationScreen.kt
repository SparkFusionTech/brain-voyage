package com.sparkfusion.quiz.brainvoyage.ui.screen.registration

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sparkfusion.quiz.brainvoyage.R
import com.sparkfusion.quiz.brainvoyage.ui.viewmodel.registration.RegistrationContract
import com.sparkfusion.quiz.brainvoyage.ui.viewmodel.registration.RegistrationViewModel
import com.sparkfusion.quiz.brainvoyage.ui.widget.SFProRoundedText

@Composable
fun RegistrationScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    viewModel: RegistrationViewModel = hiltViewModel()
) {
    val uiState = viewModel.initialState()

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        TopBar(onBackClick = onBackClick)

        Spacer(modifier = Modifier.height(20.dp))

        RegistrationTextField(
            title = stringResource(id = R.string.email),
            textFieldValue = uiState.email,
            textFieldValueChanged = {
                viewModel.handleIntent(
                    RegistrationContract.RegistrationIntent.ChangeEmail(
                        it
                    )
                )
            },
            keyboardType = KeyboardType.Email,
            trailingIcon = {
                IconButton(
                    modifier = Modifier.padding(end = 4.dp),
                    onClick = {}
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.question_icon),
                        contentDescription = stringResource(id = R.string.check_email_existence_button_icon_description)
                    )
                }
            }
        )

        Spacer(modifier = Modifier.height(10.dp))

        RegistrationTextField(
            title = stringResource(id = R.string.password),
            textFieldValue = uiState.password,
            textFieldValueChanged = {
                viewModel.handleIntent(
                    RegistrationContract.RegistrationIntent.ChangePassword(
                        it
                    )
                )
            },
            keyboardType = KeyboardType.Password,
            transformation = if (uiState.showPassword) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(
                    modifier = Modifier.padding(end = 4.dp),
                    onClick = { viewModel.handleIntent(RegistrationContract.RegistrationIntent.ChangePasswordVisibility) }
                ) {
                    Icon(
                        painter = painterResource(
                            id = if (uiState.showPassword) R.drawable.enabled_eye_icon
                            else R.drawable.disabled_eye_icon
                        ),
                        contentDescription = stringResource(id = R.string.registration_leading_icon_of_password_text_field_description)
                    )
                }
            }
        )

        Button(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 40.dp)
                .height(46.dp)
                .width(190.dp),
            onClick = { viewModel.handleIntent(RegistrationContract.RegistrationIntent.Register) }
        ) {
            SFProRoundedText(
                content = stringResource(id = R.string.register),
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun RegistrationScreenPreview() {
    RegistrationScreen(
        onBackClick = {}
    )
}