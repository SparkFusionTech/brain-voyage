package com.sparkfusion.quiz.brainvoyage.ui.screen.online.selection

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.core.graphics.toColorInt
import coil.compose.AsyncImage
import com.sparkfusion.quiz.brainvoyage.R
import com.sparkfusion.quiz.brainvoyage.ui.model.QuizCatalogSerializable
import com.sparkfusion.quiz.brainvoyage.ui.widget.SFProRoundedText

@Composable
fun CatalogItem(
    modifier: Modifier = Modifier,
    quizCatalogModel: OnlineQuizCatalogModel,
    onItemClick: (QuizCatalogSerializable) -> Unit
) {
    val targetHeight by animateDpAsState(
        label = "",
        targetValue = if (quizCatalogModel.isSelected) 100.dp else 124.dp,
        animationSpec = tween(durationMillis = 300)
    )

    Box(
        modifier = modifier
            .width(110.dp)
            .height(124.dp),
        contentAlignment = Alignment.BottomCenter,
    ) {
        Card(
            modifier = Modifier
                .size(110.dp),
            elevation = CardDefaults.cardElevation(2.dp),
            shape = RoundedCornerShape(24.dp),
            onClick = {
                val quizCatalogSerializable = QuizCatalogSerializable(
                    quizCatalogModel.id, quizCatalogModel.name
                )
                onItemClick(quizCatalogSerializable)
            }
        ) {
            Box(
                contentAlignment = Alignment.BottomCenter,
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                Color(quizCatalogModel.startGradientColor.toColorInt()),
                                Color(quizCatalogModel.endGradientColor.toColorInt())
                            )
                        ),
                        shape = RoundedCornerShape(16.dp)
                    )
            ) {
                SFProRoundedText(
                    modifier = Modifier.height(30.dp),
                    content = quizCatalogModel.name,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
            }
        }

        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = targetHeight)
                .padding(bottom = 30.dp),
            model = quizCatalogModel.imageUrl,
            contentDescription = stringResource(id = R.string.catalog_item_image_description),
            contentScale = ContentScale.Inside
        )

        AnimatedVisibility(
            quizCatalogModel.isSelected,
            enter = fadeIn(animationSpec = tween(durationMillis = 300)),
            exit = fadeOut(animationSpec = tween(durationMillis = 300))
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                Box(
                    modifier = Modifier
                        .size(110.dp)
                        .clip(RoundedCornerShape(24.dp))
                        .background(Color.Black.copy(alpha = 0.6f), RoundedCornerShape(24.dp))
                        .align(Alignment.BottomCenter)
                )
            }

            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    modifier = Modifier
                        .size(48.dp)
                        .zIndex(1f),
                    model = R.drawable.selected_item,
                    contentDescription = null
                )

                SFProRoundedText(
                    modifier = Modifier
                        .fillMaxWidth()
                        .zIndex(1f),
                    content = "Selected",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF5EAC24),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun CatalogItemPreview() {
    CatalogItem(
        quizCatalogModel = OnlineQuizCatalogModel(
            1,
            "Music",
            "link",
            "#FFBD8903",
            "#FFBD3B03"
        ),
        onItemClick = {}
    )
}