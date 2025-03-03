package com.sparkfusion.quiz.brainvoyage.ui.screen.online.selection

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sparkfusion.quiz.brainvoyage.ui.screen.online.questions.BouncingDots
import com.sparkfusion.quiz.brainvoyage.ui.theme.buttonDarkColor
import com.sparkfusion.quiz.brainvoyage.ui.theme.buttonLightColor
import com.sparkfusion.quiz.brainvoyage.ui.viewmodel.online.OnlineSelectionViewModel
import com.sparkfusion.quiz.brainvoyage.ui.widget.SFProRoundedText
import com.sparkfusion.quiz.brainvoyage.ui.widget.shimmer.ShimmerAnimationBox

@Composable
fun ColumnScope.ConnectRoomComponent(
    catalogLoadingState: OnlineSelectionViewModel.QuizCatalogLoadingState,
    showProgress: Boolean,
    onConnectClick: () -> Unit,
    onUpdateSelectedItem: (id: Long) -> Unit
) {
    if (showProgress) {
        Spacer(modifier = Modifier.weight(1f))
        SFProRoundedText(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .padding(bottom = 16.dp),
            content = "Connecting...",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            color = Color.White
        )
        BouncingDots(color = Color(0xFFBABD03))
        Spacer(modifier = Modifier.weight(1f))
    } else {
        SFProRoundedText(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .padding(top = 96.dp, bottom = 16.dp),
            content = "Select a quiz category",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            color = Color.White
        )

        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            columns = GridCells.Fixed(3)
        ) {
            when (catalogLoadingState) {
                OnlineSelectionViewModel.QuizCatalogLoadingState.Error -> {}
                OnlineSelectionViewModel.QuizCatalogLoadingState.Loading -> {
                    items(5) {
                        ShimmerAnimationBox(
                            modifier = Modifier.padding(4.dp),
                            size = DpSize(110.dp, 110.dp),
                            shape = RoundedCornerShape(24.dp),
                            isDarkModeEnabled = isSystemInDarkTheme()
                        )
                    }
                }

                OnlineSelectionViewModel.QuizCatalogLoadingState.NetworkError -> {

                }

                is OnlineSelectionViewModel.QuizCatalogLoadingState.Success -> {
                    items(catalogLoadingState.data) { model ->
                        CatalogItem(
                            modifier = Modifier.padding(top = 4.dp),
                            quizCatalogModel = model,
                            onItemClick = {
                                onUpdateSelectedItem(model.id)
                            }
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        SFProRoundedText(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .padding(bottom = 16.dp),
            content = "If you leave this as default, the quiz category will be selected randomly.",
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            color = Color.LightGray
        )

        Button(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 54.dp)
                .padding(horizontal = 64.dp)
                .fillMaxWidth()
                .height(60.dp)
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(buttonDarkColor, buttonLightColor),
                        start = Offset(150f, 0f)
                    ),
                    shape = RoundedCornerShape(50.dp)
                )
                .clip(RoundedCornerShape(50.dp)),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
            onClick = onConnectClick
        ) {
            SFProRoundedText(
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 4.dp),
                content = "Play",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun ConnectRoomComponentPreview() {
    Column {
        ConnectRoomComponent(
            catalogLoadingState = OnlineSelectionViewModel.QuizCatalogLoadingState.Success(emptyList()),
            showProgress = false,
            onConnectClick = {},
            onUpdateSelectedItem = {}
        )
    }
}























