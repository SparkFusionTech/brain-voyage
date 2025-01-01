package com.sparkfusion.quiz.brainvoyage.ui.screen.drawer.my_quizzes

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sparkfusion.quiz.brainvoyage.R
import com.sparkfusion.quiz.brainvoyage.ui.screen.drawer.my_quizzes.component.QuizItemComponent
import com.sparkfusion.quiz.brainvoyage.ui.screen.empty_loading.EmptyLoadingScreen
import com.sparkfusion.quiz.brainvoyage.ui.viewmodel.drawer.my_quizzes.MyQuizzesContract
import com.sparkfusion.quiz.brainvoyage.ui.viewmodel.drawer.my_quizzes.MyQuizzesViewModel
import com.sparkfusion.quiz.brainvoyage.ui.widget.SFProRoundedText
import com.sparkfusion.quiz.brainvoyage.utils.dp.getStatusBarHeightInDp
import com.sparkfusion.quiz.brainvoyage.window.StatusBarHeightOwner

@Composable
fun MyQuizzesScreen(
    modifier: Modifier = Modifier,
    viewModel: MyQuizzesViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    onQuizClick: (Long) -> Unit
) {
    val quizzesState by viewModel.submittedQuizzesState.collectAsStateWithLifecycle()
    viewModel.handleIntent(MyQuizzesContract.Intent.ReadQuizzes)

    val context = LocalContext.current
    val height = LocalConfiguration.current.screenHeightDp.dp - 72.dp

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .paint(
                painter = painterResource(id = R.drawable.background),
                contentScale = ContentScale.Crop
            )
    ) {
        item {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = modifier
                    .padding(
                        top = if (StatusBarHeightOwner.hasCutout) getStatusBarHeightInDp().dp else 0.dp
                    )
                    .height(72.dp)
                    .fillMaxWidth()
            ) {
                IconButton(
                    modifier = Modifier.padding(start = 8.dp),
                    onClick = onBackClick
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.arrow_back_icon),
                        contentDescription = null,
                        tint = Color.White
                    )
                }

                SFProRoundedText(
                    modifier = Modifier.padding(start = 24.dp),
                    content = "My quizzes",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White
                )
            }
        }

        when (quizzesState) {
            MyQuizzesContract.QuizzesReadingState.Loading -> {
                item {
                    EmptyLoadingScreen(modifier = Modifier.height(height))
                }
            }

            MyQuizzesContract.QuizzesReadingState.Error -> {
                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
            }

            is MyQuizzesContract.QuizzesReadingState.Success -> {
                val successState = quizzesState as MyQuizzesContract.QuizzesReadingState.Success
                val entries = successState.map.entries.toList()
                
                items(entries) { (date, quizzes) ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        SFProRoundedText(
                            modifier = Modifier
                                .background(
                                    Color.Black.copy(alpha = 0.4f),
                                    RoundedCornerShape(20.dp)
                                )
                                .padding(horizontal = 12.dp, vertical = 4.dp),
                            content = date,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = Color.White
                        )
                    }

                    quizzes.forEach {
                        QuizItemComponent(
                            quiz = it,
                            onItemClick = onQuizClick
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun MyQuizzesScreenPreview() {
    MyQuizzesScreen(
        onBackClick = {},
        onQuizClick = {}
    )
}

























