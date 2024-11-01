package com.sparkfusion.quiz.brainvoyage.ui.screen.add_quiz.add_question.component.input

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sparkfusion.quiz.brainvoyage.R
import com.sparkfusion.quiz.brainvoyage.ui.widget.SFProRoundedText
import com.sparkfusion.quiz.brainvoyage.ui.widget.text.DefaultTextField

@Composable
fun QuestionNameInputComponent(
    value: String,
    onValueChange: (String) -> Unit,
    snackbarHostState: SnackbarHostState
) {
    val nameIsTooLongMessage = stringResource(id = R.string.name_is_too_long)
    var showErrorMessage by remember { mutableStateOf(false) }
    LaunchedEffect(value) {
        if (showErrorMessage) {
            snackbarHostState.showSnackbar(nameIsTooLongMessage)
            showErrorMessage = false
        }
    }

    SFProRoundedText(
        modifier = Modifier.padding(start = 24.dp, top = 40.dp),
        content = stringResource(id = R.string.question),
        fontSize = 18.sp,
        fontWeight = FontWeight.SemiBold
    )

    DefaultTextField(
        modifier = Modifier
            .padding(start = 24.dp, end = 24.dp)
            .fillMaxWidth(),
        value = value,
        onValueChange = {
            if (value.length < 40) onValueChange(it)
            else showErrorMessage = true
        },
        placeholder = stringResource(id = R.string.enter_here)
    )
}
