package com.sparkfusion.quiz.brainvoyage.ui.screen.add_quiz.add_question.component.item

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sparkfusion.quiz.brainvoyage.R
import com.sparkfusion.quiz.brainvoyage.ui.screen.add_quiz.add_question.model.answer.QuestionAnswerModel
import com.sparkfusion.quiz.brainvoyage.ui.screen.add_quiz.add_question.model.category.CategoryType
import com.sparkfusion.quiz.brainvoyage.ui.screen.add_quiz.questions.component.CustomSnackbarWithCountdown
import com.sparkfusion.quiz.brainvoyage.ui.theme.arcoFamily
import com.sparkfusion.quiz.brainvoyage.ui.widget.SFProRoundedText
import kotlinx.coroutines.delay

@Composable
fun AnswerItemComponent(
    modifier: Modifier = Modifier,
    index: Int,
    categoryType: CategoryType,
    answer: QuestionAnswerModel,
    onRadioButtonClick: (Int) -> Unit,
    onCheckButtonClick: (Int, Boolean) -> Unit,
    onDeleteItem: (Int) -> Unit
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

    val totalDuration = 4000
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

            onDeleteItem(index)
            resetSwipe = true
            showSnackbar = false
        }
    }

    SwipeToDismissBox(
        enableDismissFromStartToEnd = false,
        enableDismissFromEndToStart = categoryType != CategoryType.TrueFalse,
        state = dismissState,
        backgroundContent = {
            if (showSnackbar) {
                val timerProgress = millisRemaining / totalDuration.toFloat()
                CustomSnackbarWithCountdown(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 4.dp)
                        .background(
                            Color.Black.copy(alpha = 0.8f),
                            shape = RoundedCornerShape(16.dp)
                        )
                        .padding(horizontal = 16.dp),
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
                        .padding(horizontal = 24.dp, vertical = 4.dp)
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .background(
                            color = if (categoryType != CategoryType.TrueFalse) MaterialTheme.colorScheme.error
                            else Color.Transparent,
                            shape = RoundedCornerShape(16.dp)
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
                    .padding(vertical = 4.dp)
                    .padding(horizontal = 24.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(
                        color = MaterialTheme.colorScheme.secondaryContainer,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .fillMaxWidth()
                    .clickable {
                        when (categoryType) {
                            CategoryType.MultiplyChoice -> onCheckButtonClick(index, answer.isCorrect)
                            CategoryType.Quiz, CategoryType.TrueFalse -> onRadioButtonClick(index)
                        }
                    }
            ) {
                Text(
                    modifier = Modifier.padding(vertical = 12.dp, horizontal = 24.dp),
                    fontFamily = arcoFamily,
                    text = "${index + 1}",
                    fontSize = 28.sp
                )

                SFProRoundedText(
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .width(220.dp),
                    content = answer.name,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                when (categoryType) {
                    CategoryType.MultiplyChoice -> {
                        Checkbox(
                            checked = answer.isCorrect,
                            onCheckedChange = { onCheckButtonClick(index, it) }
                        )
                    }

                    CategoryType.Quiz, CategoryType.TrueFalse -> {
                        RadioButton(
                            modifier = Modifier.padding(horizontal = 24.dp),
                            selected = answer.isCorrect,
                            onClick = { onRadioButtonClick(index) }
                        )
                    }
                }
            }
        }
    )
}
