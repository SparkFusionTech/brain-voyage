package com.sparkfusion.quiz.brainvoyage.ui.screen.online.questions

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sparkfusion.quiz.brainvoyage.ui.theme.buttonDarkColor
import com.sparkfusion.quiz.brainvoyage.ui.theme.buttonLightColor
import com.sparkfusion.quiz.brainvoyage.ui.theme.settingsBackgroundDarkColor
import com.sparkfusion.quiz.brainvoyage.ui.theme.settingsBackgroundLightColor
import com.sparkfusion.quiz.brainvoyage.ui.widget.SFProRoundedText

@Composable
fun ColumnScope.ConnectionLostComponent(
    message: String,
    onBackClick: () -> Unit
) {
    Spacer(modifier = Modifier.weight(1f))

    SFProRoundedText(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .padding(bottom = 16.dp),
        content = message,
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center,
        color = Color.White
    )

    Spacer(modifier = Modifier.weight(1f))

    Button(
        modifier = Modifier
            .align(Alignment.CenterHorizontally)
            .padding(bottom = 54.dp)
            .padding(horizontal = 64.dp)
            .fillMaxWidth()
            .height(60.dp)
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(buttonDarkColor, buttonLightColor),
                    start = Offset(150f, 0f)
                ),
                shape = RoundedCornerShape(50.dp)
            )
            .clip(RoundedCornerShape(50.dp)),
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
        onClick = onBackClick
    ) {
        SFProRoundedText(
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 4.dp),
            content = "Return",
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun ConnectionLostComponentPreview() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.linearGradient(
                    listOf(
                        settingsBackgroundLightColor,
                        settingsBackgroundDarkColor
                    )
                )
            )
    ) {
        ConnectionLostComponent(
            message = "Connection was lost",
            onBackClick = {}
        )
    }
}























