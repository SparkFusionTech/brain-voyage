package com.sparkfusion.quiz.brainvoyage.ui.dialog.common

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.sparkfusion.quiz.brainvoyage.ui.theme.drawerContainerDarkColor
import com.sparkfusion.quiz.brainvoyage.ui.theme.drawerContainerLightColor
import kotlinx.coroutines.delay

@Composable
fun CommonBottomSheet(
    modifier: Modifier = Modifier,
    show: Boolean,
    onDismiss: () -> Unit,
    disableClosing: Boolean = false,
    content: @Composable ColumnScope.() -> Unit
) {
    var offsetY by remember { mutableFloatStateOf(0f) }
    var sheetHeight by remember { mutableIntStateOf(0) }

    val draggableState = rememberDraggableState { delta ->
        if (!disableClosing && (delta > 0 || offsetY + delta >= 0)) {
            offsetY += delta
        }
    }

    if (show) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.4f))
                .clickable(onClick = { onDismiss() })
        )
    }

    AnimatedVisibility(
        visible = show,
        enter = fadeIn() + slideInVertically(initialOffsetY = { it }),
        exit = fadeOut() + slideOutVertically(targetOffsetY = { it }),
    ) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .zIndex(1f)
        ) {
            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .offset { IntOffset(x = 0, offsetY.toInt()) }
                    .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                    .background(Brush.verticalGradient(listOf(drawerContainerLightColor, drawerContainerDarkColor)))
                    .padding(horizontal = 24.dp)
                    .padding(vertical = 20.dp)
                    .onGloballyPositioned { layoutCoordinates ->
                        sheetHeight = layoutCoordinates.size.height
                    }
                    .draggable(
                        state = draggableState,
                        orientation = Orientation.Vertical,
                        onDragStopped = {
                            if (offsetY > sheetHeight / 2) onDismiss() else offsetY = 0f
                        }
                    )
            ) {
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .width(60.dp)
                        .height(4.dp)
                        .background(
                            brush = Brush.horizontalGradient(
                                listOf(
                                    Color.Gray,
                                    Color.Gray.copy(alpha = 0.6f)
                                )
                            ),
                            shape = RoundedCornerShape(4.dp)
                        )
                )

                content()
            }
        }
    }

    LaunchedEffect(!show) {
        delay(200L)
        offsetY = 0f
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun CommonBottomSheetPreview() {
    CommonBottomSheet(
        show = true,
        onDismiss = {}
    ) {}
}






















