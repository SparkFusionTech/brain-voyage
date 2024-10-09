package com.sparkfusion.quiz.brainvoyage.ui.drawer.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.sparkfusion.quiz.brainvoyage.R
import com.sparkfusion.quiz.brainvoyage.ui.drawer.DrawerReadingState
import com.sparkfusion.quiz.brainvoyage.ui.widget.shimmer.ShimmerAnimationBox

@Composable
fun AccountIconComponent(
    modifier: Modifier = Modifier,
    accountInfoState: DrawerReadingState
) {
    val size = remember { 96.dp }
    if (accountInfoState == DrawerReadingState.Loading) {
        ShimmerAnimationBox(
            modifier = modifier,
            size = DpSize(size, size)
        )
    } else if (accountInfoState is DrawerReadingState.Success) {
        AsyncImage(
            modifier = modifier,
            model = accountInfoState.accountInfoModel.iconUrl,
            contentDescription = stringResource(id = R.string.drawer_account_icon_description)
        )
    }
}
