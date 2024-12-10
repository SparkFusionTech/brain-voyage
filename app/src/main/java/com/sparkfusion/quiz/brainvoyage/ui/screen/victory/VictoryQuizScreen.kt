package com.sparkfusion.quiz.brainvoyage.ui.screen.victory

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sparkfusion.quiz.brainvoyage.ui.screen.victory.component.VictoryBackgroundBlock
import com.sparkfusion.quiz.brainvoyage.ui.theme.secondaryLight
import com.sparkfusion.quiz.brainvoyage.ui.viewmodel.victory.VictoryQuizViewModel
import com.sparkfusion.quiz.brainvoyage.ui.widget.SFProRoundedText
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun VictoryQuizScreen(
    modifier: Modifier = Modifier,
    viewModel: VictoryQuizViewModel,
    onBackClick: () -> Unit
) {
    val state by viewModel.initialState.collectAsStateWithLifecycle()

    BackHandler(onBack = onBackClick)

    VictoryBackgroundBlock(
        modifier = modifier
    ) {
        Column(modifier = Modifier.fillMaxHeight()) {
            state?.let {
                val nextTryTime = remember(it.nextTryAt) { formatNextTryTime(it.nextTryAt) }

                Card(
                    modifier = Modifier
                        .padding(vertical = 16.dp)
                        .align(Alignment.CenterHorizontally),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = secondaryLight)
                ) {
                    SFProRoundedText(
                        modifier = Modifier.padding(
                            vertical = 12.dp,
                            horizontal = 30.dp
                        ),
                        content = "${it.xpCount} XP",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, top = 12.dp, end = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    SFProRoundedText(
                        modifier = Modifier.weight(1f),
                        content = "Game time",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black
                    )

                    SFProRoundedText(
                        modifier = Modifier.weight(1f),
                        content = "20:40",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Black,
                        textAlign = TextAlign.End
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, top = 2.dp, end = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    SFProRoundedText(
                        modifier = Modifier.weight(1f),
                        content = "Correct answers",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black
                    )

                    SFProRoundedText(
                        modifier = Modifier.weight(1f),
                        content = "${it.correctAnswersCount}/${it.questionsCount}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Black,
                        textAlign = TextAlign.End
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    SFProRoundedText(
                        modifier = Modifier.weight(1f),
                        content = "Next try at",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Black,
                        textAlign = TextAlign.End
                    )

                    SFProRoundedText(
                        modifier = Modifier.weight(1f),
                        content = " $nextTryTime",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally)
                        .padding(start = 16.dp, top = 6.dp, end = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(
                        modifier = Modifier
                            .weight(0.8f)
                            .padding(bottom = 2.dp, end = 4.dp),
                        onClick = onBackClick
                    ) {
                        SFProRoundedText(
                            modifier = Modifier
                                .padding(horizontal = 4.dp, vertical = 2.dp)
                                .fillMaxWidth(),
                            content = "Rate",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            textAlign = TextAlign.Center
                        )
                    }

                    Button(
                        modifier = Modifier
                            .weight(1f)
                            .padding(bottom = 2.dp, start = 4.dp),
                        onClick = onBackClick
                    ) {
                        SFProRoundedText(
                            modifier = Modifier
                                .padding(horizontal = 4.dp, vertical = 2.dp)
                                .fillMaxWidth(),
                            content = "Continue",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}

private fun formatNextTryTime(nextTryDateTime: LocalDateTime): String {
    val formatter = DateTimeFormatter.ofPattern("HH:mm")
    return nextTryDateTime.format(formatter)
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun VictoryQuizScreenPreview() {
    VictoryQuizScreen(
        viewModel = hiltViewModel(),
        onBackClick = {}
    )
}