package com.sparkfusion.quiz.brainvoyage.ui.drawer.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sparkfusion.quiz.brainvoyage.ui.drawer.DrawerReadingState
import com.sparkfusion.quiz.brainvoyage.ui.widget.SFProRoundedText
import com.sparkfusion.quiz.brainvoyage.ui.widget.shimmer.ShimmerAnimationBox

@Composable
fun AccountNameComponent(
    modifier: Modifier = Modifier,
    state: DrawerReadingState,
) {
    if (state == DrawerReadingState.Loading) {
        ShimmerAnimationBox(
            modifier = modifier,
            size = DpSize(180.dp, 24.dp)
        )
    } else if (state is DrawerReadingState.Success) {
        SFProRoundedText(
            modifier = modifier,
            content = state.accountInfoModel.name,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            color = Color.White
        )
    }
}
