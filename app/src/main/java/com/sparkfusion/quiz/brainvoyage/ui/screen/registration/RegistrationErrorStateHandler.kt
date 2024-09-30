package com.sparkfusion.quiz.brainvoyage.ui.screen.registration

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.sparkfusion.quiz.brainvoyage.R
import com.sparkfusion.quiz.brainvoyage.ui.viewmodel.registration.RegistrationState

@Composable
fun RegistrationErrorStateHandler(
    state: RegistrationState,
    context: Context
) {
    when (state) {
        RegistrationState.Error -> Toast.makeText(context, stringResource(id = R.string.error), Toast.LENGTH_LONG).show()
        RegistrationState.NetworkError -> Toast.makeText(context, stringResource(id = R.string.network_error), Toast.LENGTH_LONG).show()
        RegistrationState.FailedImageHandling -> Toast.makeText(context, stringResource(id = R.string.image_handle_error), Toast.LENGTH_LONG).show()
        else -> return
    }
}
