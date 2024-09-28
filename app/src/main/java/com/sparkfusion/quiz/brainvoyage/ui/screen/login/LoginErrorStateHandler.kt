package com.sparkfusion.quiz.brainvoyage.ui.screen.login

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.sparkfusion.quiz.brainvoyage.R
import com.sparkfusion.quiz.brainvoyage.ui.viewmodel.login.LoginState

@Composable
fun LoginErrorStateHandler(state: LoginState, context: Context) {
    when (state) {
        LoginState.Error -> Toast.makeText(context, stringResource(id = R.string.error), Toast.LENGTH_LONG).show()
        LoginState.NetworkError -> Toast.makeText(context, stringResource(id = R.string.network_error), Toast.LENGTH_LONG).show()
        else -> return
    }
}