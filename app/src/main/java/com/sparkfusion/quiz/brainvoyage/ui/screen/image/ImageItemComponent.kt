package com.sparkfusion.quiz.brainvoyage.ui.screen.image

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.sparkfusion.quiz.brainvoyage.R
import com.sparkfusion.quiz.brainvoyage.domain.model.ImageSearchModel
import com.sparkfusion.quiz.brainvoyage.ui.widget.shimmer.ShimmerAnimationBox

@Composable
fun ImageItemComponent(
    modifier: Modifier = Modifier,
    context: Context,
    model: ImageSearchModel,
    onClick: (Bitmap) -> Unit
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val columnWidth = (screenWidth / 2) - 8.dp
    val aspectRatio = remember(model.height, model.width) {
        model.height.toFloat() / model.width.toFloat()
    }

    var imageLoaded by remember { mutableStateOf(false) }
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }

    Box {
        ShimmerAnimationBox(
            modifier = Modifier.fillMaxWidth(),
            size = DpSize(columnWidth, columnWidth * aspectRatio),
            shape = RoundedCornerShape(16.dp),
            isLoadingCompleted = imageLoaded
        )

        AsyncImage(
            modifier = modifier
                .clip(RoundedCornerShape(16.dp))
                .width(columnWidth)
                .height(columnWidth * aspectRatio)
                .clickable {
                    bitmap?.let { onClick(it) }
                }
                .fillMaxWidth(),
            model = ImageRequest.Builder(context)
                .data(model.url)
                .crossfade(true)
                .allowHardware(false)
                .listener { _, result ->
                    val resultDrawable = result.drawable
                    if (resultDrawable is BitmapDrawable) {
                        bitmap = resultDrawable.bitmap
                        imageLoaded = true
                    }
                }
                .build(),
            contentScale = ContentScale.FillBounds,
            contentDescription = stringResource(id = R.string.searched_image_description)
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun ImageItemComponentPreview() {
    ImageItemComponent(
        context = LocalContext.current,
        model = ImageSearchModel(
            "url",
            width = 200,
            height = 340
        ),
        onClick = {}
    )
}

