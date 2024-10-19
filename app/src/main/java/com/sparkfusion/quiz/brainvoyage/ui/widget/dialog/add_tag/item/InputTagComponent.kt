package com.sparkfusion.quiz.brainvoyage.ui.widget.dialog.add_tag.item

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.sparkfusion.quiz.brainvoyage.R
import com.sparkfusion.quiz.brainvoyage.ui.widget.text.DefaultTextField

@Composable
fun InputTagComponent(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    onButtonClick: () -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        DefaultTextField(
            modifier = Modifier.width(244.dp),
            value = value,
            onValueChange = onValueChange,
            placeholder = "Enter tag here..."
        )

        IconButton(onClick = onButtonClick) {
            Icon(
                painter = painterResource(id = R.drawable.round_send),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}

















