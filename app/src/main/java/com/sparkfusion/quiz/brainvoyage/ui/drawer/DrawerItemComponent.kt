package com.sparkfusion.quiz.brainvoyage.ui.drawer

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sparkfusion.quiz.brainvoyage.R
import com.sparkfusion.quiz.brainvoyage.ui.widget.SFProRoundedText

@Composable
fun DrawerItemComponent(
    modifier: Modifier = Modifier,
    label: String,
    icon: Painter,
    onItemClick: () -> Unit
) {
    NavigationDrawerItem(
        modifier = modifier,
        colors = NavigationDrawerItemDefaults.colors(
            selectedContainerColor = MaterialTheme.colorScheme.surface
        ),
        label = {
            SFProRoundedText(
                content = label,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onBackground
            )
        },
        icon = {
            Icon(
                modifier = Modifier
                    .padding(start = 4.dp, end = 10.dp)
                    .size(24.dp),
                painter = icon,
                contentDescription = stringResource(id = R.string.drawer_item_icon_description)
            )
        },
        selected = true,
        onClick = onItemClick
    )
}



























