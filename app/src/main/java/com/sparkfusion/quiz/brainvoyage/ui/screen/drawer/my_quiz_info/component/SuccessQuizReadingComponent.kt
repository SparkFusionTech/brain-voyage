package com.sparkfusion.quiz.brainvoyage.ui.screen.drawer.my_quiz_info.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.sparkfusion.quiz.brainvoyage.R
import com.sparkfusion.quiz.brainvoyage.domain.model.quiz.SubmittedQuizModel
import com.sparkfusion.quiz.brainvoyage.ui.screen.drawer.my_quizzes.component.statusInfoModel
import com.sparkfusion.quiz.brainvoyage.ui.viewmodel.drawer.my_quiz_info.MyQuizInfoContract
import com.sparkfusion.quiz.brainvoyage.ui.widget.SFProRoundedText
import com.sparkfusion.quiz.brainvoyage.ui.widget.shimmer.ShimmerAnimationBox
import com.sparkfusion.quiz.brainvoyage.utils.descriptionColor

@Composable
fun SuccessQuizReadingComponent(
    modifier: Modifier = Modifier,
    quiz: SubmittedQuizModel,
    tagsState: MyQuizInfoContract.TagsReadingState,
    questionsState: MyQuizInfoContract.QuestionsReadingState
) {
    var isImageLoading by remember { mutableStateOf(false) }

    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        val statusInfoModel = statusInfoModel(status = quiz.status)
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            if (isImageLoading) {
                ShimmerAnimationBox(
                    modifier = Modifier
                        .padding(top = 40.dp),
                    size = DpSize(180.dp, 200.dp),
                    shape = RoundedCornerShape(16.dp)
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
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp
                )
            }

            AsyncImage(
                modifier = Modifier
                    .padding(top = if (isImageLoading) 0.dp else 40.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .height(if (isImageLoading) 0.dp else 200.dp)
                    .width(180.dp),
                model = quiz.imageUrl,
                contentDescription = stringResource(id = R.string.quiz_image_description),
                onLoading = { isImageLoading = true },
                onSuccess = { isImageLoading = false }
            )
        }

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

        SFProRoundedText(
            modifier = Modifier.padding(top = 24.dp, start = 24.dp, end = 24.dp),
            content = quiz.description,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            color = Color.White
        )

        ListItemComponent(
            title = "Tags",
            onItemClick = {}
        )

        ListItemComponent(
            title = "Questions",
            onItemClick = {}
        )

        SFProRoundedText(
            modifier = Modifier.padding(
                start = 24.dp,
                end = 24.dp,
                top = 24.dp,
                bottom = 20.dp
            ),
            content = "Some content",
            color = Color.White
        )
    }
}






















