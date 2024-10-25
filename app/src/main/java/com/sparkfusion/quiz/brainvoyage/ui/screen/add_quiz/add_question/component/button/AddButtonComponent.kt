package com.sparkfusion.quiz.brainvoyage.ui.screen.add_quiz.add_question.component.button

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sparkfusion.quiz.brainvoyage.ui.widget.SFProRoundedText

@Composable
fun AddButtonComponent(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .padding(vertical = 8.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Button(
            colors = ButtonDefaults.buttonColors(Color.Black),
            onClick = onClick
        ) {
            SFProRoundedText(
                modifier = Modifier.padding(horizontal = 12.dp),
                content = "Add",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}
