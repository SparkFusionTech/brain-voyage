package com.sparkfusion.quiz.brainvoyage.ui.screen.add_quiz.add.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sparkfusion.quiz.brainvoyage.R
import com.sparkfusion.quiz.brainvoyage.ui.screen.online.games.BackIcon
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
        BackIcon {
            onBackClick()
        }

        Spacer(modifier = Modifier.weight(1f))

        SFProRoundedText(
            content = stringResource(id = R.string.new_quiz),
            fontSize = 20.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Color.White
        )

        Spacer(
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp)
        )

        Image(
            modifier = Modifier
                .padding(end = 16.dp)
                .clip(RoundedCornerShape(8.dp))
                .clickable { onTagSearchClick() }
                .size(24.dp),
            painter = painterResource(id = R.drawable.add_tag_icon),
            contentDescription = stringResource(id = R.string.add_tags_button_icon_description),
        )
    }
}













