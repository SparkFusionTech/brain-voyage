package com.sparkfusion.quiz.brainvoyage.ui.screen.catalog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.core.graphics.toColorInt
import coil.compose.AsyncImage
import com.sparkfusion.quiz.brainvoyage.R
import com.sparkfusion.quiz.brainvoyage.domain.model.QuizCatalogModel
import com.sparkfusion.quiz.brainvoyage.ui.widget.SFProRoundedText

@Composable
fun CatalogItem(
    modifier: Modifier = Modifier,
    quizCatalogModel: QuizCatalogModel
) {
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
            onClick = { }
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
                .heightIn(max = 124.dp)
                .padding(bottom = 30.dp)
                .zIndex(1f),
            model = quizCatalogModel.imageUrl,
            contentDescription = stringResource(id = R.string.catalog_item_image_description),
            contentScale = ContentScale.Inside
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun CatalogItemPreview() {
    CatalogItem(
        quizCatalogModel = QuizCatalogModel(
            1,
            "Music",
            "link",
            "#FFBD8903",
            "#FFBD3B03"
        )
    )
}
