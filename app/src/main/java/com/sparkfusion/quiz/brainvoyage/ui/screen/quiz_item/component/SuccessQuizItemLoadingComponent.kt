package com.sparkfusion.quiz.brainvoyage.ui.screen.quiz_item.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.sparkfusion.quiz.brainvoyage.R
import com.sparkfusion.quiz.brainvoyage.domain.model.quiz.GetQuizPreviewModel
import com.sparkfusion.quiz.brainvoyage.domain.model.quiz.rating.QuizRatingModel
import com.sparkfusion.quiz.brainvoyage.ui.theme.buttonDarkColor
import com.sparkfusion.quiz.brainvoyage.ui.theme.buttonLightColor
import com.sparkfusion.quiz.brainvoyage.ui.widget.SFProRoundedText
import com.sparkfusion.quiz.brainvoyage.ui.widget.animation.shimmerBrush
import com.sparkfusion.quiz.brainvoyage.utils.descriptionColor
import com.sparkfusion.quiz.brainvoyage.utils.dp.getStatusBarHeightInDp
import com.sparkfusion.quiz.brainvoyage.window.StatusBarHeightOwner
import java.time.Duration

@Composable
fun SuccessQuizItemLoadingComponent(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    onPlayButtonClick: () -> Unit,
    quiz: GetQuizPreviewModel,
    rating: QuizRatingModel?,
    nextTryAt: Duration?
) {
    var isImageLoading by remember { mutableStateOf(true) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                top = if (StatusBarHeightOwner.hasCutout) getStatusBarHeightInDp().dp else 0.dp
            )
    ) {
        QuizItemTopBarComponent(onBackClick = onBackClick)

        AsyncImage(
            modifier = Modifier
                .padding(top = 40.dp)
                .clip(RoundedCornerShape(16.dp))
                .align(Alignment.CenterHorizontally)
                .height(200.dp)
                .width(180.dp)
                .background(shimmerBrush(targetValue = 1300f, showShimmer = isImageLoading)),
            model = quiz.imageUrl,
            contentScale = ContentScale.Crop,
            contentDescription = stringResource(id = R.string.quiz_image_description),
            onLoading = { isImageLoading = true },
            onSuccess = { isImageLoading = false },
            onError = { isImageLoading = false }
        )

        SFProRoundedText(
            modifier = Modifier
                .padding(start = 36.dp, end = 36.dp, top = 12.dp)
                .align(Alignment.CenterHorizontally),
            content = quiz.title,
            textAlign = TextAlign.Center,
            fontSize = 22.sp,
            fontWeight = FontWeight.Black,
            color = Color.White
        )

        SFProRoundedText(
            modifier = Modifier
                .padding(top = 2.dp)
                .align(Alignment.CenterHorizontally),
            content = stringResource(id = R.string.questions_count_template, quiz.questions),
            textAlign = TextAlign.Center,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = descriptionColor()
        )

        StarRatingBar(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 16.dp),
            rating = rating?.rating ?: 0.0
        )

        SFProRoundedText(
            modifier = Modifier
                .padding(top = 2.dp)
                .align(Alignment.CenterHorizontally),
            content = if (rating == null || rating.rating == 0.0) stringResource(id = R.string.be_first)
            else stringResource(id = R.string.rating_template, rating.rating),
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = descriptionColor()
        )

        SFProRoundedText(
            modifier = Modifier.padding(top = 24.dp, start = 24.dp, end = 24.dp),
            content = quiz.description,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            color = Color.White
        )

        Button(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 40.dp, bottom = 20.dp)
                .padding(horizontal = 64.dp)
                .fillMaxWidth()
                .height(60.dp)
                .background(
                    brush = Brush.linearGradient(
                        start = Offset(x = 150f, y = 0f),
                        colors = listOf(buttonDarkColor, buttonLightColor),
                    ),
                    shape = RoundedCornerShape(50.dp)
                )
                .clip(RoundedCornerShape(50.dp)),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
            onClick = {
                if (nextTryAt == null) onPlayButtonClick()
            }
        ) {
            SFProRoundedText(
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 4.dp),
                content = stringResource(id = R.string.play),
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        if (nextTryAt != null) {
            val hours = nextTryAt.toHours()
            val minutes = nextTryAt.toMinutes() % 60
            SFProRoundedText(
                modifier = Modifier
                    .padding(top = 24.dp, start = 24.dp, end = 24.dp)
                    .align(Alignment.CenterHorizontally),
                content = "Next try in $hours:$minutes",
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = Color.White
            )
        }
    }
}










