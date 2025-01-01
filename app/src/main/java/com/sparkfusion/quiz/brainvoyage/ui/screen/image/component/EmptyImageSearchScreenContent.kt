package com.sparkfusion.quiz.brainvoyage.ui.screen.image.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.sparkfusion.quiz.brainvoyage.R
import com.sparkfusion.quiz.brainvoyage.ui.theme.settingsBackgroundDarkColor
import com.sparkfusion.quiz.brainvoyage.ui.theme.settingsBackgroundLightColor
import com.sparkfusion.quiz.brainvoyage.ui.widget.SFProRoundedText

@Composable
fun EmptyImageSearchScreenContent(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize().background(
            Brush.linearGradient(
                listOf(
                    settingsBackgroundLightColor,
                    settingsBackgroundDarkColor
                )
            )
        ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        AsyncImage(
            modifier = Modifier.size(96.dp),
            model = R.drawable.start_search_image,
            contentDescription = stringResource(id = R.string.empty_search_images_status_description)
        )

        SFProRoundedText(
            modifier = Modifier.padding(top = 20.dp),
            content = stringResource(id = R.string.enter_your_query_to_find_images),
            fontWeight = FontWeight.SemiBold,
            fontSize = 20.sp
        )
    }
}
