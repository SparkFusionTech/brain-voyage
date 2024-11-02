package com.sparkfusion.quiz.brainvoyage.ui.screen.quiz_item.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.sparkfusion.quiz.brainvoyage.R
import kotlin.math.ceil
import kotlin.math.floor

@Composable
fun StarRatingBar(
    modifier: Modifier = Modifier,
    rating: Double = 0.0,
    starsColor: Color = Color.Yellow
) {
    val filledStars = floor(rating).toInt()
    val unfilledStars = (5 - ceil(rating)).toInt()
    val halfStar = !(rating.rem(1).equals(0.0))

    Row(modifier = modifier) {
        repeat(filledStars) {
            Icon(
                modifier = Modifier
                    .size(40.dp)
                    .padding(horizontal = 3.dp),
                painter = painterResource(id = R.drawable.filled_star_icon),
                contentDescription = stringResource(id = R.string.rating_filled_star_icon_description),
                tint = starsColor
            )
        }

        if (halfStar) {
            Icon(
                modifier = Modifier
                    .size(40.dp)
                    .padding(horizontal = 3.dp),
                painter = painterResource(id = R.drawable.half_star_icon),
                contentDescription = stringResource(id = R.string.rating_half_star_icon_description),
                tint = starsColor
            )
        }

        repeat(unfilledStars) {
            Icon(
                modifier = Modifier
                    .size(40.dp)
                    .padding(horizontal = 3.dp),
                painter = painterResource(id = R.drawable.outlined_star_icon),
                contentDescription = stringResource(id = R.string.rating_outlined_star_icon_description),
                tint = starsColor
            )
        }
    }
}

