package com.sparkfusion.quiz.brainvoyage.window.splash

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sparkfusion.quiz.brainvoyage.R
import com.sparkfusion.quiz.brainvoyage.ui.widget.SFProRoundedText
import kotlinx.coroutines.delay

@Composable
fun SplashContent(isAnimationFinished: MutableState<Boolean>) {
    val scale = remember { Animatable(0.0f) }
    val isTextVisible = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        scale.animateTo(
            targetValue = 1f,
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessLow
            )
        )
        isTextVisible.value = true

        delay(1200)
        isAnimationFinished.value = true
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(modifier = Modifier.weight(1f))

            Image(
                modifier = Modifier
                    .size(220.dp)
                    .scale(scale.value)
                    .padding(bottom = 20.dp),
                painter = painterResource(id = R.drawable.splash_preview),
                contentDescription = stringResource(id = R.string.splash_image_description)
            )

            Row(
                modifier = Modifier.padding(bottom = 80.dp),
                horizontalArrangement = Arrangement.Center,
            ) {
                AnimatedSplashText(isTextVisible = isTextVisible.value)
            }

            Spacer(modifier = Modifier.weight(1f))

            SFProRoundedText(
                modifier = Modifier
                    .padding(bottom = 4.dp)
                    .fillMaxWidth(),
                content = stringResource(id = R.string.loading_with_ellipsis),
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center,
                color = Color.Gray
            )

            LinearProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 32.dp, end = 32.dp, bottom = 80.dp)
                    .height(8.dp),
                color = splashLinearProgressBarColor,
                trackColor = splashLinearProgressBarStoreColor,
                strokeCap = StrokeCap.Round
            )
        }
    }
}
