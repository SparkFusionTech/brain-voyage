package com.sparkfusion.quiz.brainvoyage.ui.screen.registration

import android.graphics.Bitmap
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sparkfusion.quiz.brainvoyage.ui.screen.registration.component.AccountIconComponent
import com.sparkfusion.quiz.brainvoyage.ui.screen.registration.component.EmailTextFieldComponent
import com.sparkfusion.quiz.brainvoyage.ui.screen.registration.component.PasswordTextFieldComponent
import com.sparkfusion.quiz.brainvoyage.ui.screen.registration.component.RegistrationButtonComponent
import com.sparkfusion.quiz.brainvoyage.ui.screen.registration.component.TopBar
import com.sparkfusion.quiz.brainvoyage.ui.screen.registration.handler.HandleUserExistenceMessageShowing
import com.sparkfusion.quiz.brainvoyage.ui.screen.registration.handler.RegistrationErrorStateHandler
import com.sparkfusion.quiz.brainvoyage.ui.viewmodel.registration.RegistrationContract
import com.sparkfusion.quiz.brainvoyage.ui.viewmodel.registration.RegistrationState
import com.sparkfusion.quiz.brainvoyage.ui.viewmodel.registration.RegistrationViewModel
import com.sparkfusion.quiz.brainvoyage.utils.dp.getStatusBarHeightInDp
import com.sparkfusion.quiz.brainvoyage.window.StatusBarHeightOwner

@Composable
fun RegistrationScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    navigateToImageCrop: (Bitmap) -> Unit,
    saveRegistrationDataAndExit: (email: String, password: String) -> Unit,
    getCroppedImageBitmap: () -> Bitmap?,
    viewModel: RegistrationViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val uiState by viewModel.initialState().collectAsStateWithLifecycle()
    viewModel.handleIntent(
        RegistrationContract.RegistrationIntent.ChangeAccountIcon(
            getCroppedImageBitmap()
        )
    )

    if (uiState.registrationState is RegistrationState.Success) {
        val success = uiState.registrationState as RegistrationState.Success
        saveRegistrationDataAndExit(
            success.model.email,
            uiState.password
        )
    }

    HandleUserExistenceMessageShowing(context = context, userExistsState = uiState.userExistsState)
    RegistrationErrorStateHandler(state = uiState.registrationState, context = context)

    Column(
        modifier = modifier
        .fillMaxSize()
        .background(Color.White)
    ) {
        TopBar(
            modifier = Modifier.padding(
                top = if (StatusBarHeightOwner.hasCutout) getStatusBarHeightInDp().dp else 0.dp
            ),
            onBackClick = onBackClick
        )

        Spacer(modifier = Modifier.height(30.dp))

        AccountIconComponent(
            context = context,
            registrationState = uiState.registrationState,
            accountIcon = uiState.accountIcon,
            onNavigateToImageCrop = navigateToImageCrop,
            onClearRegistrationState = { viewModel.handleIntent(RegistrationContract.RegistrationIntent.ClearRegistrationState) }
        )

        Spacer(modifier = Modifier.height(30.dp))

        EmailTextFieldComponent(
            userExistsState = uiState.userExistsState,
            registrationState = uiState.registrationState,
            email = uiState.email,
            onTextFieldValueChange = {
                viewModel.handleIntent(
                    RegistrationContract.RegistrationIntent.ChangeEmail(it)
                )
            },
            onIconButtonClick = {
                viewModel.handleIntent(RegistrationContract.RegistrationIntent.CheckUserExistence)
            }
        )

        Spacer(modifier = Modifier.height(10.dp))

        PasswordTextFieldComponent(
            password = uiState.password,
            showPassword = uiState.showPassword,
            onTextFieldChange = {
                viewModel.handleIntent(
                    RegistrationContract.RegistrationIntent.ChangePassword(it)
                )
            },
            onIconButtonClick = {
                viewModel.handleIntent(RegistrationContract.RegistrationIntent.ChangePasswordVisibility)
            }
        )

        RegistrationButtonComponent(
            registrationState = uiState.registrationState,
            onButtonClick = { viewModel.handleIntent(RegistrationContract.RegistrationIntent.Register) }
        )
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
