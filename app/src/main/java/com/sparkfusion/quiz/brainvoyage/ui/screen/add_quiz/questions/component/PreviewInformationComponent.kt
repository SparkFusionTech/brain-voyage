package com.sparkfusion.quiz.brainvoyage.ui.screen.add_quiz.questions.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.sparkfusion.quiz.brainvoyage.R
import com.sparkfusion.quiz.brainvoyage.ui.screen.add_quiz.model.AddQuizInitialModel
import com.sparkfusion.quiz.brainvoyage.ui.widget.SFProRoundedText

@Composable
fun PreviewInformationComponent(
    modifier: Modifier = Modifier,
    model: AddQuizInitialModel
) {
    Column(modifier = modifier) {
        Spacer(modifier = Modifier.height(30.dp))
        AsyncImage(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .clip(RoundedCornerShape(16.dp))
                .height(200.dp)
                .width(180.dp),
            model = model.bitmap,
            contentScale = ContentScale.Crop,
            contentDescription = stringResource(id = R.string.adding_quiz_image_description)
        )

        SFProRoundedText(
            modifier = Modifier
                .padding(start = 24.dp, end = 24.dp, top = 16.dp)
                .fillMaxWidth(),
            content = model.title,
            fontSize = 22.sp,
            fontWeight = FontWeight.Black,
            textAlign = TextAlign.Center
        )

        SFProRoundedText(
            modifier = Modifier
                .padding(start = 24.dp, end = 24.dp, top = 16.dp)
                .fillMaxWidth(),
            content = model.description,
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center
        )

        SFProRoundedText(
            modifier = Modifier
                .padding(start = 24.dp, end = 24.dp, top = 24.dp, bottom = 4.dp)
                .fillMaxWidth(),
            content = stringResource(id = R.string.add_questions_only_on_the_selected_topic),
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
        )
    }
}























