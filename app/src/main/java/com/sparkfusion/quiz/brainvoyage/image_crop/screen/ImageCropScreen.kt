package com.sparkfusion.quiz.brainvoyage.image_crop.screen

import android.graphics.Bitmap
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.sparkfusion.quiz.brainvoyage.R
import com.sparkfusion.quiz.brainvoyage.image_crop.common.ImageCropType
import com.sparkfusion.quiz.brainvoyage.image_crop.handler.ImageCropper
import com.sparkfusion.quiz.brainvoyage.image_crop.space.CircleCropSpace
import com.sparkfusion.quiz.brainvoyage.image_crop.space.DynamicRectangleCropSpace
import com.sparkfusion.quiz.brainvoyage.image_crop.space.RectangleCropSpace
import com.sparkfusion.quiz.brainvoyage.image_crop.view.ActionButton
import com.sparkfusion.quiz.brainvoyage.ui.theme.backgroundDark
import kotlinx.coroutines.launch

@Composable
fun ImageCropScreen(
    modifier: Modifier = Modifier,
    cropType: ImageCropType,
    bitmap: Bitmap,
    onCropClickHandler: (Bitmap) -> Unit
) {
    val image = bitmap.asImageBitmap()
    val coroutineScope = rememberCoroutineScope()

    val imageCropper by remember { mutableStateOf(ImageCropper(cropType, image)) }
    var croppedImage by remember { mutableStateOf<ImageBitmap?>(null) }

    val configuration = LocalConfiguration.current
    val screenWidthPx = with(LocalDensity.current) { configuration.screenWidthDp.dp.toPx() }
    val minSide = minOf(image.width, image.height).toFloat()

    var scale by remember { mutableFloatStateOf(1f) }
    var transform by remember { mutableStateOf(Offset(0f, 0f)) }

    LaunchedEffect(croppedImage) {
        croppedImage?.let {
            onCropClickHandler(it.asAndroidBitmap())
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(backgroundDark)
    ) {
        val (cropWidth, cropHeight) = when (cropType) {
            ImageCropType.RectangleCrop -> {
                val cropRectangleWidth = minOf(screenWidthPx, minSide)
                val cropRectangleHeight = cropRectangleWidth * 2 / 3

                RectangleCropSpace(
                    modifier = Modifier.fillMaxSize(),
                    cropWidth = cropRectangleWidth,
                    cropHeight = cropRectangleHeight,
                    image = image,
                    onScaleChange = { scale = it },
                    onTransformChange = { transform = it }
                )

                Pair(cropRectangleWidth, cropRectangleHeight)
            }

            is ImageCropType.DynamicRectangleCrop -> {
                val cropRectangleWidthDp = cropType.widthDp
                val cropRectangleHeightDp = cropType.heightDp

                DynamicRectangleCropSpace(
                    modifier = Modifier.fillMaxSize(),
                    cropWidthDp = cropRectangleWidthDp,
                    cropHeightDp = cropRectangleHeightDp,
                    image = image,
                    onScaleChange = { scale = it },
                    onTransformChange = { transform = it }
                )

                with(LocalDensity.current) {
                    Pair(
                        cropRectangleWidthDp.toPx(),
                        cropRectangleHeightDp.toPx()
                    )
                }
            }

            else -> {
                val cropCircleDiameter = if (screenWidthPx < minSide) screenWidthPx else minSide

                CircleCropSpace(
                    modifier = Modifier.fillMaxSize(),
                    cropCircleDiameter = cropCircleDiameter,
                    image = image,
                    onScaleChange = { scale = it },
                    onTransformChange = { transform = it }
                )

                Pair(cropCircleDiameter, cropCircleDiameter)
            }
        }

        ActionButton(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(vertical = 20.dp, horizontal = 24.dp),
            icon = painterResource(id = R.drawable.round_crop),
            onClick = {
                coroutineScope.launch {
                    croppedImage = imageCropper.cropImage(
                        cropWidth = cropWidth,
                        cropHeight = cropHeight,
                        transform = transform,
                        scale = scale
                    ).asImageBitmap()
                }
            }
        )
    }
}















