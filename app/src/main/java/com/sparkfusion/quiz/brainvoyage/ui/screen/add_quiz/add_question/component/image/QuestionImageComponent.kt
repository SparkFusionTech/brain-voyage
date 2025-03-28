package com.sparkfusion.quiz.brainvoyage.ui.screen.add_quiz.add_question.component.image

import android.graphics.Bitmap
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.sparkfusion.quiz.brainvoyage.R

@Composable
fun QuestionImageComponent(
    modifier: Modifier = Modifier,
    bitmap: Bitmap?,
    changeImageSelectionDialogVisibility: (Boolean) -> Unit
) {
    Column(modifier = modifier.fillMaxWidth()) {
        if (bitmap == null) {
            Spacer(modifier = Modifier.height(40.dp))
            AsyncImage(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .clip(RoundedCornerShape(12.dp))
                    .clickable { changeImageSelectionDialogVisibility(true) }
                    .size(128.dp),
                model = R.drawable.select_image_icon,
                contentDescription = stringResource(id = R.string.empty_selected_image_icon_description)
            )
        } else {
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
                    .clip(RoundedCornerShape(bottomEnd = 20.dp, bottomStart = 20.dp))
                    .clickable { changeImageSelectionDialogVisibility(true) },
                model = bitmap,
                contentScale = ContentScale.Crop,
                contentDescription = stringResource(id = R.string.question_image_description)
            )
        }
    }
}
