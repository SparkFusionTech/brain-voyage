package com.sparkfusion.quiz.brainvoyage.ui.screen.add_quiz.add_question.component.image

import android.graphics.Bitmap
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
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
                    .clickable { changeImageSelectionDialogVisibility(true) }
                    .size(128.dp),
                model = R.drawable.select_image_icon,
                contentDescription = null
            )
        } else {
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
                    .clickable { changeImageSelectionDialogVisibility(true) },
                model = bitmap,
                contentScale = ContentScale.Fit,
                contentDescription = null
            )
        }
    }
}
