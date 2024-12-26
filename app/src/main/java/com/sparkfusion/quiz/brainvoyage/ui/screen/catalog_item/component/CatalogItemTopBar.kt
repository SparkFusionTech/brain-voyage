package com.sparkfusion.quiz.brainvoyage.ui.screen.catalog_item.component

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sparkfusion.quiz.brainvoyage.R
import com.sparkfusion.quiz.brainvoyage.ui.widget.SFProRoundedText

@Composable
fun CatalogItemTopBar(
    modifier: Modifier = Modifier,
    title: String,
    onMenuClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .height(64.dp)
            .fillMaxWidth()
    ) {
        IconButton(
            modifier = Modifier
                .padding(start = 12.dp)
                .size(32.dp),
            onClick = onMenuClick
        ) {
            Icon(
                modifier = Modifier.size(28.dp),
                painter = painterResource(id = R.drawable.round_menu),
                contentDescription = stringResource(id = R.string.drawer_menu_icon_description),
                tint = Color.White
            )
        }

        SFProRoundedText(
            modifier = Modifier.padding(start = 20.dp),
            content = title,
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp,
            color = Color.White
        )

        Spacer(modifier = Modifier.weight(1f))

        IconButton(
            modifier = Modifier
                .padding(end = 12.dp)
                .size(32.dp),
            onClick = {}
        ) {
            Icon(
                painter = painterResource(id = R.drawable.filters_icon),
                contentDescription = stringResource(id = R.string.filters_button_icon_description),
                tint = Color.White,
            )
        }
    }
}
