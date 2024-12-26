package com.sparkfusion.quiz.brainvoyage.ui.screen.add_quiz.add.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sparkfusion.quiz.brainvoyage.R
import com.sparkfusion.quiz.brainvoyage.ui.theme.buttonDarkColor
import com.sparkfusion.quiz.brainvoyage.ui.theme.buttonLightColor
import com.sparkfusion.quiz.brainvoyage.ui.widget.SFProRoundedText

@Composable
fun NextButtonComponent(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier
            .padding(top = 36.dp)
            .fillMaxWidth()
            .height(60.dp)
            .padding(horizontal = 32.dp)
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(buttonLightColor, buttonDarkColor),
                ),
                shape = RoundedCornerShape(50.dp)
            )
            .clip(RoundedCornerShape(50.dp)),
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
    ) {
        SFProRoundedText(
            modifier = Modifier
                .padding(vertical = 8.dp),
            content = stringResource(id = R.string.next),
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.White
        )
    }
}
