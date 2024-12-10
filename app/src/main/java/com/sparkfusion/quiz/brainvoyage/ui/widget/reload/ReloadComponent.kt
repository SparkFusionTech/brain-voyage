package com.sparkfusion.quiz.brainvoyage.ui.widget.reload

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sparkfusion.quiz.brainvoyage.R
import com.sparkfusion.quiz.brainvoyage.ui.widget.SFProRoundedText

@Composable
fun ReloadComponent(
    modifier: Modifier = Modifier,
    onReloadClick: () -> Unit,
    topBar: @Composable () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Transparent),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        topBar()

        Image(
            modifier = Modifier.size(128.dp),
            painter = painterResource(id = R.drawable.error_icon),
            contentDescription = stringResource(id = R.string.loading_error_screen_image_description)
        )

        SFProRoundedText(
            modifier = Modifier
                .width(260.dp)
                .padding(top = 24.dp),
            content = stringResource(id = R.string.an_error_has_occurred),
            fontWeight = FontWeight.SemiBold,
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            color = Color.White
        )

        Button(
            modifier = Modifier.padding(top = 24.dp),
            onClick = onReloadClick
        ) {
            SFProRoundedText(
                content = stringResource(id = R.string.reload),
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun ReloadComponentPreview() {
    ReloadComponent(
        onReloadClick = {},
        topBar = {}
    )
}






















