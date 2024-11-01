package com.sparkfusion.quiz.brainvoyage.ui.screen.registration.component

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.sparkfusion.quiz.brainvoyage.R
import com.sparkfusion.quiz.brainvoyage.ui.launcher.rememberLauncherForImageCropping
import com.sparkfusion.quiz.brainvoyage.ui.viewmodel.registration.RegistrationState

@Composable
fun ColumnScope.AccountIconComponent(
    context: Context,
    registrationState: RegistrationState,
    accountIcon: Bitmap?,
    onNavigateToImageCrop: (Bitmap) -> Unit,
    onClearRegistrationState: () -> Unit
) {
    val galleryLauncher = rememberLauncherForImageCropping(
        context = context,
        navigateToImageCrop = onNavigateToImageCrop
    )

    AsyncImage(
        modifier = Modifier
            .clip(CircleShape)
            .size(156.dp)
            .fillMaxSize()
            .align(Alignment.CenterHorizontally)
            .clickable {
                onClearRegistrationState()
                galleryLauncher.launch("image/*")
            },
        model = if (registrationState == RegistrationState.FailedImageHandling) R.drawable.empty_account_icon
        else accountIcon ?: R.drawable.empty_account_icon,
        contentDescription = stringResource(id = R.string.register_account_image_description),
        onError = {
            Log.i("TAGTAG", it.result.throwable.message ?: "Unknown error")
        }
    )
}
