package com.sparkfusion.quiz.brainvoyage.ui.screen.drawer.my_quizzes.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.sparkfusion.quiz.brainvoyage.R
import com.sparkfusion.quiz.brainvoyage.domain.model.quiz.QuizStatusModel
import com.sparkfusion.quiz.brainvoyage.domain.model.quiz.SubmittedQuizModel
import com.sparkfusion.quiz.brainvoyage.ui.widget.SFProRoundedText
import com.sparkfusion.quiz.brainvoyage.ui.widget.shimmer.ShimmerAnimationBox
import com.sparkfusion.quiz.brainvoyage.utils.descriptionColor
import java.time.LocalDateTime

@Composable
fun QuizItemComponent(
    modifier: Modifier = Modifier,
    quiz: SubmittedQuizModel,
    onItemClick: (Long) -> Unit
) {
    var isImageLoading by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .clickable { onItemClick(quiz.id) }
    ) {
        if (isImageLoading) {
            ShimmerAnimationBox(
                modifier = Modifier.padding(start = 12.dp, top = 6.dp, bottom = 6.dp, end = 12.dp),
                size = DpSize(90.dp, 100.dp),
                shape = RoundedCornerShape(16.dp)
            )
        }

        val paddings = if (isImageLoading) PaddingValues()
        else PaddingValues(start = 12.dp, top = 6.dp, bottom = 6.dp, end = 12.dp)

        val statusInfoModel = statusInfoModel(status = quiz.status)
        Box(
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                modifier = Modifier
                    .padding(paddings)
                    .clip(RoundedCornerShape(16.dp))
                    .size(if (isImageLoading) DpSize(0.dp, 0.dp) else DpSize(90.dp, 100.dp)),
                model = ImageRequest.Builder(context)
                    .data(quiz.imageUrl)
                    .crossfade(true)
                    .diskCachePolicy(CachePolicy.ENABLED)
                    .memoryCachePolicy(CachePolicy.ENABLED)
                    .build(),
                contentScale = ContentScale.Crop,
                contentDescription = stringResource(id = R.string.quiz_preview_image_description),
                onLoading = { isImageLoading = true },
                onSuccess = { isImageLoading = false }
            )

            if (statusInfoModel.color != Color.Transparent) {
                Box(
                    modifier = Modifier
                        .size(DpSize(90.dp, 100.dp))
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.Black.copy(alpha = 0.4f))
                )
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    modifier = Modifier.size(if (statusInfoModel.color == Color.Transparent) 0.dp else 24.dp),
                    painter = painterResource(id = statusInfoModel.icon),
                    contentDescription = null,
                    tint = statusInfoModel.color
                )

                SFProRoundedText(
                    modifier = Modifier
                        .padding(top = 4.dp)
                        .width(90.dp),
                    content = statusInfoModel.text,
                    color = statusInfoModel.color,
                    fontWeight = FontWeight.Medium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 6.dp)
        ) {
            SFProRoundedText(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .width(260.dp),
                content = quiz.title,
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            SFProRoundedText(
                content = stringResource(
                    id = R.string.questions_count_template,
                    quiz.questions
                ),
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = descriptionColor()
            )

            SFProRoundedText(
                modifier = Modifier
                    .padding(top = 4.dp)
                    .width(260.dp),
                content = quiz.description,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun QuizItemComponentPreview() {
    QuizItemComponent(
        quiz = SubmittedQuizModel(
            1L,
            "Super super class",
            "description hljsfd fds g fdg sdf g dsfg sdgsdf gsd fg sdf gdsfgsfdgdsf gdf gdf g",
            QuizStatusModel.MODIFY,
            2.3,
            "",
            7,
            createdAt = LocalDateTime.MIN
        ),
        onItemClick = {}
    )
}



















