package com.sparkfusion.quiz.brainvoyage.ui.screen.registration

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.sparkfusion.quiz.brainvoyage.R
import com.sparkfusion.quiz.brainvoyage.ui.viewmodel.registration.RegistrationState
import com.sparkfusion.quiz.brainvoyage.ui.viewmodel.registration.UserExistsState

@Composable
fun EmailTextFieldComponent(
    userExistsState: UserExistsState,
    registrationState: RegistrationState,
    email: String,
    onTextFieldValueChange: (String) -> Unit,
    onIconButtonClick: () -> Unit
) {
    RegistrationTextField(
        title = stringResource(id = R.string.email),
        textFieldValue = email,
        textFieldValueChanged = onTextFieldValueChange,
        isError = registrationState == RegistrationState.UserAlreadyExists,
        keyboardType = KeyboardType.Email,
        trailingIcon = {
            if (userExistsState == UserExistsState.Loading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(32.dp)
                        .padding(end = 4.dp)
                )
            } else {
                IconButton(
                    modifier = Modifier.padding(end = 4.dp),
                    onClick = onIconButtonClick
                ) {
                    Icon(
                        painter = getUserExistenceIconByState(userExistsState = userExistsState),
                        tint = getUserExistenceIconTintByState(userExistsState = userExistsState),
                        contentDescription = stringResource(id = R.string.check_email_existence_button_icon_description)
                    )
                }
            }
        }
    )
}
