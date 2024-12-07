package com.sparkfusion.quiz.brainvoyage.ui.screen.quiz_item.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.sparkfusion.quiz.brainvoyage.R
import com.sparkfusion.quiz.brainvoyage.domain.model.quiz.GetQuizPreviewModel
import com.sparkfusion.quiz.brainvoyage.ui.widget.SFProRoundedText
import com.sparkfusion.quiz.brainvoyage.ui.widget.shimmer.ShimmerAnimationBox
import com.sparkfusion.quiz.brainvoyage.utils.descriptionColor
import com.sparkfusion.quiz.brainvoyage.utils.dp.getStatusBarHeightInDp
import com.sparkfusion.quiz.brainvoyage.window.StatusBarHeightOwner

@Composable
fun SuccessQuizItemLoadingComponent(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    quiz: GetQuizPreviewModel
) {
    var isImageLoading by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        AsyncImage(
            modifier = Modifier.fillMaxSize(),
            model = R.drawable.play_quiz_background,
            contentDescription = null,
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(
                    top = if (StatusBarHeightOwner.hasCutout) getStatusBarHeightInDp().dp else 0.dp
                )
        ) {
            QuizItemTopBarComponent(onBackClick = onBackClick)

            if (isImageLoading) {
                ShimmerAnimationBox(
                    modifier = Modifier
                        .padding(top = 40.dp)
                        .align(Alignment.CenterHorizontally),
                    size = DpSize(180.dp, 200.dp),
                    shape = RoundedCornerShape(16.dp)
                )
            }

            AsyncImage(
                modifier = Modifier
                    .padding(top = if (isImageLoading) 0.dp else 40.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .align(Alignment.CenterHorizontally)
                    .height(if (isImageLoading) 0.dp else 200.dp)
                    .width(180.dp),
                model = quiz.imageUrl,
                contentDescription = stringResource(id = R.string.quiz_image_description),
                onLoading = { isImageLoading = true },
                onSuccess = { isImageLoading = false }
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
                rating = quiz.rating
            )

            SFProRoundedText(
                modifier = Modifier
                    .padding(top = 2.dp)
                    .align(Alignment.CenterHorizontally),
                content = if (quiz.rating < 1f) stringResource(id = R.string.be_first)
                else stringResource(id = R.string.rating_template, quiz.rating),
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
                    .padding(top = 40.dp, bottom = 20.dp),
                onClick = { }
            ) {
                SFProRoundedText(
                    modifier = Modifier.padding(horizontal = 24.dp, vertical = 4.dp),
                    content = stringResource(id = R.string.play),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}
