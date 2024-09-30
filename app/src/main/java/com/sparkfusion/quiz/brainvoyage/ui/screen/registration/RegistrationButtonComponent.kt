package com.sparkfusion.quiz.brainvoyage.ui.screen.registration

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sparkfusion.quiz.brainvoyage.R
import com.sparkfusion.quiz.brainvoyage.ui.viewmodel.registration.RegistrationState
import com.sparkfusion.quiz.brainvoyage.ui.widget.SFProRoundedText

@Composable
fun ColumnScope.RegistrationButtonComponent(
    registrationState: RegistrationState,
    onButtonClick: () -> Unit
) {
    Button(
        modifier = Modifier
            .align(Alignment.CenterHorizontally)
            .padding(top = 40.dp)
            .height(46.dp)
            .width(190.dp),
        onClick = onButtonClick
    ) {
        if (registrationState == RegistrationState.Loading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .size(32.dp),
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
