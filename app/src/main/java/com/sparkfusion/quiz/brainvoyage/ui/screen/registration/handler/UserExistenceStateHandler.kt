package com.sparkfusion.quiz.brainvoyage.ui.screen.registration.handler

import android.content.Context
import android.widget.Toast
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import com.sparkfusion.quiz.brainvoyage.R
import com.sparkfusion.quiz.brainvoyage.ui.viewmodel.registration.UserExistsState

@Composable
fun HandleUserExistenceMessageShowing(
    context: Context,
    userExistsState: UserExistsState
) {
    LaunchedEffect(userExistsState) {
        if (userExistsState == UserExistsState.Exists) {
            Toast.makeText(context, R.string.already_exists, Toast.LENGTH_SHORT).show()
        } else if (userExistsState == UserExistsState.Error) {
            Toast.makeText(context, R.string.error, Toast.LENGTH_SHORT).show()
        }
    }
}

@Composable
fun getUserExistenceIconByState(userExistsState: UserExistsState): Painter {
    return painterResource(
        id = when (userExistsState) {
            UserExistsState.Exists -> R.drawable.round_error
            UserExistsState.NotExists -> R.drawable.round_check
            UserExistsState.Loading -> R.drawable.question_icon
            UserExistsState.Empty -> R.drawable.question_icon
            UserExistsState.Error -> R.drawable.question_icon
        }
    )
}

@Composable
fun getUserExistenceIconTintByState(userExistsState: UserExistsState): Color {
    return when (userExistsState) {
        UserExistsState.Exists -> MaterialTheme.colorScheme.error
        UserExistsState.NotExists -> Color.Green
        else -> LocalContentColor.current
    }
}
