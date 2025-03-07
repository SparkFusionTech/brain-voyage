package com.sparkfusion.quiz.brainvoyage.ui.screen.online.victory

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.sparkfusion.quiz.brainvoyage.R
import com.sparkfusion.quiz.brainvoyage.ui.viewmodel.online.Online2VS2GameViewModel
import com.sparkfusion.quiz.brainvoyage.ui.widget.SFProRoundedText
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

val xpColor = Color(0xFFFCD71D)

@Composable
fun OnlineVictoryScreen(
    modifier: Modifier = Modifier,
    viewModel: Online2VS2GameViewModel,
    onBackClick: () -> Unit
) {
    val reason by viewModel.reason.collectAsStateWithLifecycle()
    val victoryState by viewModel.victoryState.collectAsStateWithLifecycle()

    val coroutineScope = rememberCoroutineScope()
    val scale = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            delay(200L)
            scale.animateTo(
                targetValue = 1f,
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
        }
    }

    BackHandler {
        onBackClick()
    }

    if (reason is Online2VS2GameViewModel.VictoryReason.GameEnd || reason is Online2VS2GameViewModel.VictoryReason.OpponentDisconnected) {
        Column(
            modifier = modifier
                .paint(
                    painter = painterResource(id = R.drawable.planets_orbiting_space_background),
                    contentScale = ContentScale.Crop
                )
                .background(Color.Black.copy(alpha = 0.5f))
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(420.dp)
            ) {
                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .scale(scale.value),
                    painter = painterResource(id = R.drawable.victory_trophy),
                    contentDescription = null,
                    contentScale = ContentScale.FillWidth
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 180.dp),
                    contentAlignment = Alignment.Center
                ) {
                    AsyncImage(
                        modifier = Modifier
                            .size(220.dp)
                            .clip(RoundedCornerShape(16.dp)),
                        model = R.drawable.victory_account_light,
                        contentDescription = null,
                        contentScale = ContentScale.Crop
                    )

                    AsyncImage(
                        modifier = Modifier
                            .size(96.dp)
                            .clip(RoundedCornerShape(16.dp)),
                        model = victoryState.account?.iconUrl,
                        contentDescription = null,
                        contentScale = ContentScale.Crop
                    )

                    Image(
                        modifier = Modifier
                            .padding(start = 64.dp, top = 72.dp)
                            .size(36.dp),
                        painter = painterResource(id = R.drawable.medal_trophy),
                        contentDescription = null
                    )

                    Column(
                        modifier = Modifier.padding(top = 180.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        SFProRoundedText(
                            modifier = Modifier,
                            content = victoryState.account?.name ?: "",
                            color = Color.White,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 22.sp
                        )

                        SFProRoundedText(
                            modifier = Modifier,
                            content = "${victoryState.account?.score ?: 0} XP",
                            color = xpColor,
                            fontWeight = FontWeight.Medium,
                            fontSize = 18.sp,
                        )
                    }
                }
            }

            when (reason) {
                is Online2VS2GameViewModel.VictoryReason.GameEnd -> {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 40.dp)
                            .height(72.dp)
                            .padding(horizontal = 24.dp)
                            .background(Color.Black.copy(alpha = 0.5f), RoundedCornerShape(20.dp)),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        SFProRoundedText(
                            modifier = Modifier.padding(start = 24.dp),
                            content = "2",
                            color = Color.White,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 20.sp
                        )

                        AsyncImage(
                            modifier = Modifier
                                .padding(start = 24.dp)
                                .size(48.dp)
                                .clip(CircleShape),
                            model = victoryState.opponent?.iconUrl,
                            contentDescription = null
                        )

                        SFProRoundedText(
                            modifier = Modifier
                                .padding(start = 16.dp)
                                .width(140.dp),
                            content = victoryState.opponent?.name ?: "",
                            color = Color.White,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 18.sp,
                            overflow = TextOverflow.Ellipsis
                        )

                        Spacer(modifier = Modifier.weight(1f))

                        SFProRoundedText(
                            modifier = Modifier.padding(end = 16.dp),
                            content = "${victoryState.opponent?.score ?: 0} XP",
                            color = xpColor,
                            fontWeight = FontWeight.Medium,
                            fontSize = 18.sp,
                        )
                    }
                }

                is Online2VS2GameViewModel.VictoryReason.OpponentDisconnected -> {
                    SFProRoundedText(
                        modifier = Modifier
                            .padding(horizontal = 24.dp)
                            .padding(top = 40.dp),
                        content = "Your opponent has disconnected, you are the winner!",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center
                    )
                }

                else -> {}
            }

            Spacer(modifier = Modifier.weight(1f))

            TextButton(
                modifier = Modifier.padding(bottom = 56.dp),
                onClick = onBackClick
            ) {
                SFProRoundedText(
                    content = "Click to continue",
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 22.sp
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun OnlineVictoryScreenPreview() {
    OnlineVictoryScreen(
        viewModel = hiltViewModel(),
        onBackClick = {}
    )
}





















