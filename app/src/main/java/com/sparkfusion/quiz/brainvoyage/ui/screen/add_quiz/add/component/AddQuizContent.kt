package com.sparkfusion.quiz.brainvoyage.ui.screen.add_quiz.add.component

import android.graphics.Bitmap
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.sparkfusion.quiz.brainvoyage.R
import com.sparkfusion.quiz.brainvoyage.ui.widget.SFProRoundedText
import com.sparkfusion.quiz.brainvoyage.ui.widget.text.DefaultTextField
import com.sparkfusion.quiz.brainvoyage.ui.widget.text.DescriptionText
import com.sparkfusion.quiz.brainvoyage.ui.widget.text.TitleText
import com.sparkfusion.quiz.brainvoyage.utils.descriptionColor

@Composable
fun AddQuizContent(
    modifier: Modifier = Modifier,
    bitmap: Bitmap?,
    onImageAddClick: () -> Unit,
    title: String,
    description: String,
    onTitleChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onNextButtonClick: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(30.dp))
        AsyncImage(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .clip(RoundedCornerShape(16.dp))
                .clickable { onImageAddClick() }
                .height(200.dp)
                .width(180.dp),
            model = bitmap ?: R.drawable.select_image_icon,
            contentScale = ContentScale.Fit,
            contentDescription = stringResource(id = R.string.selected_quiz_image_description)
        )

        Row(
            modifier = Modifier.padding(start = 24.dp, end = 24.dp, top = 40.dp)
        ) {
            TitleText(content = stringResource(id = R.string.title))

            SFProRoundedText(
                modifier = Modifier.padding(start = 4.dp),
                content = stringResource(id = R.string.max_32),
                fontWeight = FontWeight.Medium,
                color = descriptionColor()
            )
        }

        DefaultTextField(
            modifier = Modifier
                .padding(start = 24.dp, end = 24.dp)
                .fillMaxWidth(),
            value = title,
            keyboardType = KeyboardType.Text,
            onValueChange = onTitleChange,
            singleLine = true,
            placeholder = stringResource(id = R.string.enter_here)
        )

        DescriptionText(
            modifier = Modifier.padding(start = 24.dp, end = 24.dp),
            content = stringResource(id = R.string.do_not_forget_to_add_search_keywords)
        )

        TitleText(
            modifier = Modifier.padding(start = 24.dp, top = 24.dp),
            content = stringResource(id = R.string.description),
        )

        DefaultTextField(
            modifier = Modifier
                .height(120.dp)
                .padding(start = 24.dp, end = 24.dp)
                .fillMaxWidth(),
            value = description,
            keyboardType = KeyboardType.Text,
            onValueChange = onDescriptionChange,
            placeholder = stringResource(id = R.string.enter_here)
        )

        DescriptionText(
            modifier = Modifier.padding(start = 24.dp, end = 24.dp),
            content = stringResource(id = R.string.add_a_description_so_users_can)
        )

        NextButtonComponent { onNextButtonClick() }
    }
}




















