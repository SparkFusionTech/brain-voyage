package com.sparkfusion.quiz.brainvoyage.ui.dialog.rating

import android.view.MotionEvent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sparkfusion.quiz.brainvoyage.R
import com.sparkfusion.quiz.brainvoyage.ui.dialog.common.CommonBottomSheet
import com.sparkfusion.quiz.brainvoyage.ui.theme.buttonDarkColor
import com.sparkfusion.quiz.brainvoyage.ui.theme.buttonLightColor
import com.sparkfusion.quiz.brainvoyage.ui.widget.SFProRoundedText

@Composable
fun QuizRatingBottomSheet(
    show: Boolean,
    initialState: Int? = null,
    onDismiss: () -> Unit,
    onSuccess: (Int) -> Unit
) {
    CommonBottomSheet(
        show = show,
        onDismiss = onDismiss
    ) {
        QuizRatingBottomSheetContent(
            initialState = initialState,
            onDismiss = onDismiss,
            onSuccess = onSuccess
        )
    }
}

@Composable
private fun QuizRatingBottomSheetContent(
    initialState: Int? = null,
    onDismiss: () -> Unit,
    onSuccess: (Int) -> Unit
) {
    val rating = remember { mutableIntStateOf(initialState ?: 0) }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        RatingBar(ratingState = rating)

        SFProRoundedText(
            modifier = Modifier
                .padding(top = 24.dp)
                .fillMaxWidth(),
            content = "Rate the completed quiz. The rating can be changed the next time you play!",
            color = Color.LightGray,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Start
        )

        Button(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 12.dp, bottom = 24.dp)
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
            onClick = {
                if (rating.intValue == 0) onDismiss()
                else onSuccess(rating.intValue)
            }
        ) {
            SFProRoundedText(
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 4.dp),
                content = "Confirm",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
fun RatingBar(
    modifier: Modifier = Modifier,
    size: Dp = 48.dp,
    ratingState: MutableState<Int> = remember { mutableIntStateOf(0) },
    ratingIconPainter: Painter = painterResource(id = R.drawable.filled_star_icon),
    selectedColor: Color = Color(0xFFFFD700),
    unselectedColor: Color = Color(0xFFA2ADB1)
) {
    Row(
        modifier = modifier.wrapContentSize()
    ) {
        for (value in 1..5) {
            StarIcon(
                size = size,
                painter = ratingIconPainter,
                ratingValue = value,
                ratingState = ratingState,
                selectedColor = selectedColor,
                unselectedColor = unselectedColor
            )

            if (value != 5) Spacer(modifier = Modifier.width(16.dp))
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun StarIcon(
    size: Dp,
    ratingState: MutableState<Int>,
    painter: Painter,
    ratingValue: Int,
    selectedColor: Color,
    unselectedColor: Color
) {
    val tint by animateColorAsState(
        animationSpec = tween(500),
        targetValue = if (ratingValue <= ratingState.value) selectedColor else unselectedColor,
        label = ""
    )

    Icon(
        painter = painter,
        contentDescription = null,
        modifier = Modifier
            .size(size)
            .pointerInteropFilter {
                when (it.action) {
                    MotionEvent.ACTION_DOWN -> {
                        ratingState.value = ratingValue
                    }
                }

                true
            },
        tint = tint
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun QuizRatingBottomSheetPreview() {
    QuizRatingBottomSheet(
        initialState = 3,
        show = true,
        onDismiss = {},
        onSuccess = {}
    )
}




























