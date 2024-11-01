package com.sparkfusion.quiz.brainvoyage.ui.screen.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.sparkfusion.quiz.brainvoyage.R
import com.sparkfusion.quiz.brainvoyage.ui.screen.login.component.EnterComponent
import com.sparkfusion.quiz.brainvoyage.ui.screen.login.handler.LoginErrorStateHandler
import com.sparkfusion.quiz.brainvoyage.ui.theme.arcoFamily
import com.sparkfusion.quiz.brainvoyage.ui.viewmodel.login.LoginContract
import com.sparkfusion.quiz.brainvoyage.ui.viewmodel.login.LoginState
import com.sparkfusion.quiz.brainvoyage.ui.viewmodel.login.LoginViewModel
import com.sparkfusion.quiz.brainvoyage.ui.widget.SFProRoundedText

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = hiltViewModel(),
    navigateToRegistrationScreen: () -> Unit,
    navigateToQuizCatalogScreen: () -> Unit,
    fetchRegistrationData: () -> Pair<String?, String?>
) {
    val uiState by viewModel.initialState().collectAsStateWithLifecycle()
    viewModel.handleIntent(LoginContract.LoginIntent.HandleRegistrationData(fetchRegistrationData()))

    val context = LocalContext.current
    LoginErrorStateHandler(state = uiState.loginState, context = context)

    if (uiState.loginState == LoginState.Success) navigateToQuizCatalogScreen()

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        AsyncImage(
            modifier = Modifier
                .height(240.dp)
                .fillMaxWidth(),
            contentScale = ContentScale.Crop,
            model = R.drawable.login_preview_image,
            contentDescription = stringResource(id = R.string.login_screen_preview_image_description)
        )

        Column(
            modifier = Modifier
                .padding(top = 210.dp)
                .background(
                    MaterialTheme.colorScheme.background,
                    RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp)
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.padding(top = 70.dp),
                text = stringResource(id = R.string.login),
                fontFamily = arcoFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 22.sp
            )

            EnterComponent(
                email = uiState.email,
                emailValueChanged = { viewModel.handleIntent(LoginContract.LoginIntent.ChangeEmail(it)) },
                password = uiState.password,
                passwordValueChanged = { viewModel.handleIntent(LoginContract.LoginIntent.ChangePassword(it)) }
            )

            Button(
                modifier = Modifier
                    .padding(top = 30.dp)
                    .width(180.dp)
                    .height(46.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                onClick = { viewModel.handleIntent(LoginContract.LoginIntent.Login) }
            ) {
                if (uiState.loginState == LoginState.Loading) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .size(32.dp),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                } else {
                    SFProRoundedText(
                        content = stringResource(id = R.string.login),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            TextButton(
                modifier = Modifier.padding(bottom = 12.dp),
                onClick = navigateToRegistrationScreen
            ) {
                SFProRoundedText(
                    content = stringResource(id = R.string.register_new_account),
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun LoginScreenPreview() {
    LoginScreen(
        navigateToRegistrationScreen = {},
        fetchRegistrationData = { Pair(null, null) },
        navigateToQuizCatalogScreen = {}
    )
}
