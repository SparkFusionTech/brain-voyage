package com.sparkfusion.quiz.brainvoyage.ui.dialog.add_tag.item

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.sparkfusion.quiz.brainvoyage.R
import com.sparkfusion.quiz.brainvoyage.ui.widget.text.DefaultTextField

@Composable
fun InputTagComponent(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    onButtonClick: () -> Unit
) {
    DefaultTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        placeholder = "Enter tag here...",
        trailingIcon = {
            IconButton(onClick = onButtonClick) {
                Icon(
                    painter = painterResource(id = R.drawable.round_send),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    )
}

















