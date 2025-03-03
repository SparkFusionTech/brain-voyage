package com.sparkfusion.quiz.brainvoyage.ui.screen.catalog

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.sparkfusion.quiz.brainvoyage.R
import com.sparkfusion.quiz.brainvoyage.ui.model.QuizCatalogSerializable
import com.sparkfusion.quiz.brainvoyage.ui.screen.catalog.component.CatalogItem
import com.sparkfusion.quiz.brainvoyage.ui.theme.buttonDarkColor
import com.sparkfusion.quiz.brainvoyage.ui.theme.buttonLightColor
import com.sparkfusion.quiz.brainvoyage.ui.viewmodel.quiz_catalog.QuizCatalogLoadingState
import com.sparkfusion.quiz.brainvoyage.ui.viewmodel.quiz_catalog.QuizCatalogViewModel
import com.sparkfusion.quiz.brainvoyage.ui.widget.SFProRoundedText
import com.sparkfusion.quiz.brainvoyage.ui.widget.shimmer.ShimmerAnimationBox
import com.sparkfusion.quiz.brainvoyage.utils.dp.getStatusBarHeightInDp
import com.sparkfusion.quiz.brainvoyage.window.StatusBarHeightOwner

private const val DEFAULT_CATALOG_ITEMS_COUNT = 5

@Composable
fun CatalogScreen(
    modifier: Modifier = Modifier,
    viewModel: QuizCatalogViewModel = hiltViewModel(),
    onNavigateToCatalogItemScreen: (QuizCatalogSerializable) -> Unit,
    onNavigateToOnlineGamesScreen: () -> Unit
) {
    val isDarkModeEnabled = isSystemInDarkTheme()
    val state by viewModel.initialState().collectAsStateWithLifecycle()
    val quizCatalog = if (state.catalogLoadingState is QuizCatalogLoadingState.Success) {
        val success = state.catalogLoadingState as QuizCatalogLoadingState.Success
        success.data
    } else emptyList()

    Column(
        modifier = modifier
            .paint(
                painter = painterResource(id = R.drawable.background),
                contentScale = ContentScale.Crop
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 24.dp,
                    end = 24.dp,
                    top = if (StatusBarHeightOwner.hasCutout) getStatusBarHeightInDp().dp else 0.dp
                )
                .height(72.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                modifier = Modifier
                    .width(60.dp)
                    .height(66.dp),
                model = R.drawable.catalog_preview_image,
                contentDescription = stringResource(id = R.string.catalog_preview_image_description)
            )

            SFProRoundedText(
                modifier = Modifier.padding(start = 8.dp, top = 6.dp),
                content = stringResource(id = R.string.quiz_catalog),
                fontWeight = FontWeight.Black,
                fontSize = 26.sp,
                color = Color.White
            )
        }

        SFProRoundedText(
            modifier = Modifier.padding(start = 24.dp, end = 24.dp),
            content = stringResource(id = R.string.quiz_catalog_preview),
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium
        )

        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            columns = GridCells.Fixed(3)
        ) {
            val isLoading = state.catalogLoadingState == QuizCatalogLoadingState.Loading
            if (isLoading) {
                items(DEFAULT_CATALOG_ITEMS_COUNT) {
                    ShimmerAnimationBox(
                        modifier = Modifier.padding(4.dp),
                        size = DpSize(110.dp, 110.dp),
                        shape = RoundedCornerShape(24.dp),
                        isDarkModeEnabled = isDarkModeEnabled
                    )
                }
            }

            items(quizCatalog.size) { index ->
                CatalogItem(
                    modifier = Modifier.padding(top = 4.dp),
                    quizCatalogModel = quizCatalog[index],
                    onNavigateToCatalogItemScreen = onNavigateToCatalogItemScreen
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 32.dp)
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
            onClick = onNavigateToOnlineGamesScreen
        ) {
            SFProRoundedText(
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 4.dp),
                content = "Online",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun CatalogScreenPreview() {
    CatalogScreen(
        onNavigateToCatalogItemScreen = {},
        onNavigateToOnlineGamesScreen = {}
    )
}
