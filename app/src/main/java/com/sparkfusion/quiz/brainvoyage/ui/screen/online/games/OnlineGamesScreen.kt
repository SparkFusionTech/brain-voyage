package com.sparkfusion.quiz.brainvoyage.ui.screen.online.games

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.sparkfusion.quiz.brainvoyage.R
import com.sparkfusion.quiz.brainvoyage.ui.viewmodel.online.OnlineGamesViewModel
import com.sparkfusion.quiz.brainvoyage.ui.widget.SFProRoundedText
import com.sparkfusion.quiz.brainvoyage.utils.dp.getStatusBarHeightInDp
import com.sparkfusion.quiz.brainvoyage.window.StatusBarHeightOwner

private val lightBackground = Color(0xFFBABD03)
private val darkBackground = Color(0xFF956301)

@Composable
fun OnlineGamesScreen(
    modifier: Modifier = Modifier,
    viewModel: OnlineGamesViewModel = hiltViewModel(),
    onSelectionScreenNavigate: () -> Unit,
    onBackClick: () -> Unit
) {
    val games by viewModel.games.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = if (StatusBarHeightOwner.hasCutout) getStatusBarHeightInDp().dp else 0.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                BackIcon(onBackClick = onBackClick)

                Spacer(modifier = Modifier.weight(1f))

                SFProRoundedText(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp, end = 54.dp),
                    content = stringResource(id = R.string.games),
                    color = Color.White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            }
        }
    ) { padding ->
        LazyVerticalGrid(
            modifier = modifier
                .fillMaxSize()
                .paint(
                    painter = painterResource(id = R.drawable.background),
                    contentScale = ContentScale.Crop
                )
                .padding(padding),
            columns = GridCells.Fixed(2)
        ) {
            items(games) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .background(
                            Brush.verticalGradient(listOf(lightBackground, darkBackground)),
                            RoundedCornerShape(20.dp)
                        )
                        .clickable { onSelectionScreenNavigate() },
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AsyncImage(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp)
                            .padding(horizontal = 32.dp),
                        model = it.drawable,
                        contentDescription = null,
                        contentScale = ContentScale.FillWidth
                    )

                    SFProRoundedText(
                        modifier = Modifier.padding(top = 4.dp, bottom = 12.dp),
                        content = stringResource(id = it.name),
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 28.sp
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun OnlineGamesScreenPreview() {
    OnlineGamesScreen(
        onSelectionScreenNavigate = {},
        onBackClick = {}
    )
}























