package com.sparkfusion.quiz.brainvoyage.ui.screen.registration

import android.graphics.Bitmap
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
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
import coil.compose.AsyncImage
import com.sparkfusion.quiz.brainvoyage.R
import com.sparkfusion.quiz.brainvoyage.ui.launcher.rememberLauncherForImageCropping
import com.sparkfusion.quiz.brainvoyage.ui.viewmodel.registration.RegistrationContract
import com.sparkfusion.quiz.brainvoyage.ui.viewmodel.registration.RegistrationState
import com.sparkfusion.quiz.brainvoyage.ui.viewmodel.registration.RegistrationViewModel
import com.sparkfusion.quiz.brainvoyage.ui.widget.SFProRoundedText

@Composable
fun RegistrationScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    navigateToImageCrop: (Bitmap) -> Unit,
    saveRegistrationDataAndExit: (email: String, password: String) -> Unit,
    getCroppedImageBitmap: () -> Bitmap?,
    viewModel: RegistrationViewModel = hiltViewModel()
) {
    val uiState = viewModel.initialState()
    viewModel.handleIntent(
        RegistrationContract.RegistrationIntent.ChangeAccountIcon(
            getCroppedImageBitmap()
        )
    )

    val context = LocalContext.current
    val galleryLauncher = rememberLauncherForImageCropping(
        context = context,
        navigateToImageCrop = navigateToImageCrop
    )

    if (uiState.registrationState is RegistrationState.Success) {
        saveRegistrationDataAndExit(
            uiState.registrationState.model.email,
            uiState.password
        )
    }

    RegistrationErrorStateHandler(state = uiState.registrationState, context = context)

    Column(modifier = modifier.fillMaxWidth()) {
        TopBar(onBackClick = onBackClick)

        Spacer(modifier = Modifier.height(30.dp))

        AsyncImage(
            modifier = Modifier
                .clip(CircleShape)
                .size(156.dp)
                .fillMaxSize()
                .align(Alignment.CenterHorizontally)
                .clickable {
                    viewModel.handleIntent(RegistrationContract.RegistrationIntent.ClearRegistrationState)
                    galleryLauncher.launch("image/*")
                },
            model = if (uiState.registrationState == RegistrationState.FailedImageHandling) R.drawable.empty_account_icon
            else uiState.accountIcon ?: R.drawable.empty_account_icon,
            contentDescription = stringResource(id = R.string.register_account_image_description),
            onError = {
                Log.i("TAGTAG", it.result.throwable.message ?: "Unknown error")
            }
        )

        Spacer(modifier = Modifier.height(30.dp))

        RegistrationTextField(
            title = stringResource(id = R.string.email),
            textFieldValue = uiState.email,
            textFieldValueChanged = {
                viewModel.handleIntent(
                    RegistrationContract.RegistrationIntent.ChangeEmail(it)
                )
            },
            isError = uiState.registrationState == RegistrationState.UserAlreadyExists,
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
            if (uiState.registrationState == RegistrationState.Loading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.CenterVertically).size(32.dp),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            } else {
                SFProRoundedText(
                    content = stringResource(id = R.string.register),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun RegistrationScreenPreview() {
    RegistrationScreen(
        onBackClick = {},
        navigateToImageCrop = {},
        getCroppedImageBitmap = { null },
        saveRegistrationDataAndExit = { _, _ -> }
    )
}