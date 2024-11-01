package com.sparkfusion.quiz.brainvoyage.ui.screen.catalog_item.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.sparkfusion.quiz.brainvoyage.R
import com.sparkfusion.quiz.brainvoyage.domain.model.quiz.GetQuizPreviewModel
import com.sparkfusion.quiz.brainvoyage.ui.widget.SFProRoundedText
import com.sparkfusion.quiz.brainvoyage.utils.descriptionColor

@Composable
fun QuizItemComponent(
    modifier: Modifier = Modifier,
    quiz: GetQuizPreviewModel,
    onItemClick: (Long) -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .clickable { onItemClick(quiz.id) }
    ) {
        AsyncImage(
            modifier = Modifier
                .padding(start = 12.dp, top = 6.dp, bottom = 6.dp, end = 12.dp)
                .clip(RoundedCornerShape(16.dp))
                .size(90.dp),
            model = quiz.imageUrl,
            contentDescription = stringResource(id = R.string.quiz_preview_image_description)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 6.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    SFProRoundedText(
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .width(200.dp),
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
                }

                if (quiz.rating >= 1.0) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        AsyncImage(
                            modifier = Modifier
                                .padding(top = 4.dp)
                                .size(28.dp),
                            model = R.drawable.rating_start_icon,
                            contentDescription = stringResource(id = R.string.rating_star_icon_description)
                        )

                        SFProRoundedText(
                            modifier = Modifier.padding(top = 2.dp),
                            content = stringResource(id = R.string.rating_template, quiz.rating),
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Normal
                        )
                    }
                }
            }

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
        quiz = GetQuizPreviewModel(
            1L,
            "Super super class",
            "description hljsfd fds g fdg sdf g dsfg sdgsdf gsd fg sdf gdsfgsfdgdsf gdf gdf g",
            2.3,
            "",
            7
        ),
        onItemClick = {}
    )
}



















