package com.sparkfusion.quiz.brainvoyage.ui.screen.catalog_item

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sparkfusion.quiz.brainvoyage.R
import com.sparkfusion.quiz.brainvoyage.ui.model.QuizCatalogSerializable
import com.sparkfusion.quiz.brainvoyage.ui.widget.SFProRoundedText

@Composable
fun CatalogItemScreen(
    modifier: Modifier = Modifier,
    quizCatalogSerializable: QuizCatalogSerializable,
    onNavigateToQuizAddScreen: () -> Unit
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        SFProRoundedText(content = quizCatalogSerializable.name)
        LargeFloatingActionButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(12.dp),
            onClick = onNavigateToQuizAddScreen
        ) {
            Icon(
                painter = painterResource(id = R.drawable.plus_icon),
                contentDescription = null
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun CatalogItemScreenPreview() {
    CatalogItemScreen(
        quizCatalogSerializable = QuizCatalogSerializable(1, ""),
        onNavigateToQuizAddScreen = {}
    )
}
