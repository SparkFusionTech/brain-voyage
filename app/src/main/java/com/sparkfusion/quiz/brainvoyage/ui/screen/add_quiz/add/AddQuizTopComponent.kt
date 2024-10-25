package com.sparkfusion.quiz.brainvoyage.ui.screen.add_quiz.add

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
fun AddQuizTopComponent(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    onTagSearchClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = modifier
            .height(72.dp)
            .fillMaxWidth()
    ) {
        IconButton(
            modifier = Modifier.padding(start = 8.dp),
            onClick = onBackClick
        ) {
            Icon(
                painter = painterResource(id = R.drawable.arrow_back_icon),
                contentDescription = null
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        SFProRoundedText(
            content = "New Quiz",
            fontSize = 20.sp,
            fontWeight = FontWeight.ExtraBold
        )

        Spacer(modifier = Modifier.weight(1f))

        Image(
            modifier = Modifier
                .padding(end = 12.dp)
                .clickable { onTagSearchClick() }
                .size(24.dp),
            painter = painterResource(id = R.drawable.add_tag_icon),
            contentDescription = null,
        )
    }
}
