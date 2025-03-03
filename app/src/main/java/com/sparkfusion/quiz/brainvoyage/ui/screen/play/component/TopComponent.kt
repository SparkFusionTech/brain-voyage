package com.sparkfusion.quiz.brainvoyage.ui.screen.play.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.sparkfusion.quiz.brainvoyage.R
import com.sparkfusion.quiz.brainvoyage.domain.model.question.QuestionWithAnswersModel
import com.sparkfusion.quiz.brainvoyage.ui.viewmodel.play.PlayQuizContract
import com.sparkfusion.quiz.brainvoyage.ui.widget.SFProRoundedText
import com.sparkfusion.quiz.brainvoyage.window.splash.splashLinearProgressBarColor
import com.sparkfusion.quiz.brainvoyage.window.splash.splashLinearProgressBarStoreColor
import java.text.DecimalFormat

@Composable
fun TopComponent(
    modifier: Modifier = Modifier,
    question: QuestionWithAnswersModel,
    completedValueState: PlayQuizContract.CompletedValueState,
    onCloseButtonClick: () -> Unit
) {
    Column {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(240.dp)
                .background(color = Color.Transparent)
        ) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp)
                    .clip(RoundedCornerShape(0.dp, 0.dp, 20.dp, 20.dp)),
                model = question.imageUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop
            )

            Box(
                modifier = Modifier
                    .padding(top = 24.dp, end = 12.dp)
                    .clip(CircleShape)
                    .size(44.dp)
                    .background(Color.Black.copy(alpha = 0.4f))
                    .align(Alignment.TopEnd),
                contentAlignment = Alignment.Center
            ) {
                IconButton(onClick = onCloseButtonClick) {
                    Icon(
                        painter = painterResource(id = R.drawable.exit_icon),
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .clip(RoundedCornerShape(16.dp, 16.dp, 20.dp, 20.dp))
                    .background(Color.Black.copy(alpha = 0.4f))
            ) {
                SFProRoundedText(
                    modifier = Modifier.padding(
                        vertical = 12.dp,
                        horizontal = 20.dp
                    ),
                    content = question.name,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        AnimatedLinearProgressIndicator(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 32.dp, end = 32.dp, bottom = 30.dp)
                .height(12.dp),
            completedValueState = completedValueState
        )
    }
}

@Composable
fun ColumnScope.AnimatedLinearProgressIndicator(
    modifier: Modifier = Modifier,
    completedValueState: PlayQuizContract.CompletedValueState
) {
    val progress by animateFloatAsState(
        targetValue = completedValueState.percent / 100f,
        animationSpec = tween(durationMillis = 500),
        label = ""
    )
    val decimalFormat = DecimalFormat("#.0")
    val formattedPercent = decimalFormat.format(completedValueState.percent)

    SFProRoundedText(
        modifier = Modifier
            .padding(
                top = 12.dp,
                bottom = 4.dp,
                start = 24.dp,
                end = 24.dp
            )
            .align(Alignment.CenterHorizontally),
        content = "Passed $formattedPercent %",
        fontSize = 20.sp,
        fontWeight = FontWeight.SemiBold,
        color = Color.White
    )

    LinearProgressIndicator(
        modifier = modifier,
        color = splashLinearProgressBarColor,
        trackColor = splashLinearProgressBarStoreColor,
        strokeCap = StrokeCap.Round,
        progress = { progress }
    )
}





























