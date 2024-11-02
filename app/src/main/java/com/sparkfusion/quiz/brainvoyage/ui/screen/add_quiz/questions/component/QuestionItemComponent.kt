package com.sparkfusion.quiz.brainvoyage.ui.screen.add_quiz.questions.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.sparkfusion.quiz.brainvoyage.R
import com.sparkfusion.quiz.brainvoyage.ui.screen.add_quiz.add_question.model.question.SendQuestionModel
import com.sparkfusion.quiz.brainvoyage.ui.widget.SFProRoundedText
import com.sparkfusion.quiz.brainvoyage.ui.widget.star.StarCanvas
import com.sparkfusion.quiz.brainvoyage.utils.descriptionColor
import kotlinx.coroutines.delay

@Composable
fun QuestionItemComponent(
    modifier: Modifier = Modifier,
    model: SendQuestionModel,
    deleteMessageDuration: Int = 5,
    onDeleteItem: () -> Unit
) {
    var resetSwipe by rememberSaveable { mutableStateOf(false) }
    var showSnackbar by rememberSaveable { mutableStateOf(false) }
    val dismissState = rememberSwipeToDismissBoxState(
        initialValue = SwipeToDismissBoxValue.Settled,
        confirmValueChange = {
            if (it == SwipeToDismissBoxValue.EndToStart) showSnackbar = true
            true
        },
        positionalThreshold = { totalDistance -> totalDistance * 0.7f }
    )

    val totalDuration = remember(deleteMessageDuration) { deleteMessageDuration * 1000 }
    var millisRemaining by rememberSaveable { mutableIntStateOf(totalDuration) }

    LaunchedEffect(resetSwipe) {
        if (resetSwipe) {
            dismissState.reset()
            resetSwipe = false
        }
    }

    LaunchedEffect(showSnackbar) {
        if (showSnackbar) {
            while (millisRemaining > 0) {
                delay(40)
                millisRemaining -= 40
            }

            onDeleteItem()
            showSnackbar = false
        }
    }

    SwipeToDismissBox(
        enableDismissFromStartToEnd = false,
        state = dismissState,
        backgroundContent = {
            if (showSnackbar) {
                val timerProgress = millisRemaining / totalDuration.toFloat()
                CustomSnackbarWithCountdown(
                    message = stringResource(id = R.string.do_you_want_cancel_deletion),
                    timerProgress = timerProgress,
                    secondsRemaining = (millisRemaining / 1000).coerceAtLeast(0),
                    onDismiss = {
                        showSnackbar = false
                        resetSwipe = true
                        millisRemaining = totalDuration
                    },
                    color = MaterialTheme.colorScheme.error
                )
            } else {
                Box(
                    modifier = Modifier
                        .padding(start = 24.dp, end = 24.dp, top = 2.dp, bottom = 4.dp)
                        .fillMaxWidth()
                        .height(96.dp)
                        .background(
                            color = MaterialTheme.colorScheme.error,
                            shape = RoundedCornerShape(20.dp)
                        )
                ) {
                    Icon(
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .padding(end = 32.dp)
                            .size(36.dp),
                        painter = painterResource(id = R.drawable.trash_icon),
                        contentDescription = stringResource(id = R.string.question_swipe_deletion_icon_description),
                        tint = Color.White
                    )
                }
            }
        },
        content = {
            Row(
                modifier = modifier
                    .padding(start = 24.dp, end = 24.dp, top = 2.dp, bottom = 4.dp)
                    .background(
                        color = MaterialTheme.colorScheme.primaryContainer,
                        shape = RoundedCornerShape(20.dp)
                    )
                    .height(96.dp)
                    .fillMaxWidth()
            ) {
                AsyncImage(
                    modifier = Modifier
                        .padding(24.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .size(48.dp),
                    model = model.icon,
                    contentScale = ContentScale.Fit,
                    contentDescription = stringResource(id = R.string.question_item_image_description)
                )

                Column(
                    modifier = Modifier.padding(),
                    verticalArrangement = Arrangement.Center,
                ) {
                    Row(
                        modifier = Modifier.padding(top = 24.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        SFProRoundedText(
                            content = model.name,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )

                        StarCanvas(
                            modifier = Modifier.padding(start = 4.dp),
                            sizeDp = 28.dp,
                            cornerRadiusDp = 2.dp,
                            difficulty = model.difficulty
                        )
                    }

                    SFProRoundedText(
                        content = stringResource(
                            id = R.string.answer_options_template,
                            model.answers.size
                        ),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = descriptionColor()
                    )
                }
            }
        }
    )
}





























