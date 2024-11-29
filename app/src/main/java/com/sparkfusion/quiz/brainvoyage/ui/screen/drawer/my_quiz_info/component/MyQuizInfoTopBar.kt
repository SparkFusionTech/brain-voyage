package com.sparkfusion.quiz.brainvoyage.ui.screen.drawer.my_quiz_info.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.sparkfusion.quiz.brainvoyage.R

@Composable
fun MyQuizInfoTopBar(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .height(72.dp)
            .fillMaxWidth()
    ) {
        IconButton(
            modifier = Modifier.padding(start = 8.dp),
            onClick = onBackClick
        ) {
            Icon(
                painter = painterResource(id = R.drawable.arrow_back_icon),
                contentDescription = null
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        IconButton(
            modifier = Modifier.padding(end = 8.dp),
            onClick = onDeleteClick
        ) {
            Icon(
                painter = painterResource(id = R.drawable.trash_icon),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.error
            )
        }
    }
}
