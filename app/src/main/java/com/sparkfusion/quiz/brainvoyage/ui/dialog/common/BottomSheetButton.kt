package com.sparkfusion.quiz.brainvoyage.ui.dialog.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sparkfusion.quiz.brainvoyage.ui.widget.SFProRoundedText

@Composable
fun BottomSheetButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    text: String,
    textColor: Color,
    containerColor: Color
) {
    Button(
        modifier = modifier
            .height(52.dp)
            .padding(vertical = 4.dp)
            .fillMaxWidth(),
        onClick = onClick,
        shape = RoundedCornerShape(24.dp),
        colors = ButtonDefaults.buttonColors(containerColor = containerColor)
    ) {
        SFProRoundedText(
            content = text,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = textColor
        )
    }
}














