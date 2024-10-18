package com.sparkfusion.quiz.brainvoyage.ui.widget.dialog.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sparkfusion.quiz.brainvoyage.R
import com.sparkfusion.quiz.brainvoyage.ui.widget.SFProRoundedText

@Composable
fun TitleComponent(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier
                .height(26.dp)
                .padding(end = 10.dp),
            painter = painterResource(id = R.drawable.image_selection_dialog_icon),
            contentDescription = null
        )

        SFProRoundedText(
            content = "Image Selection",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
