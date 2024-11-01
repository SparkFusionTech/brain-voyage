package com.sparkfusion.quiz.brainvoyage.ui.screen.add_quiz.add.component

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sparkfusion.quiz.brainvoyage.R
import com.sparkfusion.quiz.brainvoyage.ui.widget.SFProRoundedText

@Composable
fun ColumnScope.NextButtonComponent(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier
            .padding(top = 36.dp)
            .width(190.dp)
            .align(Alignment.CenterHorizontally),
        onClick = onClick
    ) {
        SFProRoundedText(
            modifier = Modifier,
            content = stringResource(id = R.string.next),
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}
