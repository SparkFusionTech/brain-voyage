package com.sparkfusion.quiz.brainvoyage.ui.screen.online.selection

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sparkfusion.quiz.brainvoyage.R
import com.sparkfusion.quiz.brainvoyage.domain.model.online.OnlineGameEvent
import com.sparkfusion.quiz.brainvoyage.ui.screen.online.games.BackIcon
import com.sparkfusion.quiz.brainvoyage.ui.viewmodel.online.Online2VS2GameViewModel
import com.sparkfusion.quiz.brainvoyage.ui.viewmodel.online.OnlineSelectionViewModel
import com.sparkfusion.quiz.brainvoyage.utils.dp.getStatusBarHeightInDp
import com.sparkfusion.quiz.brainvoyage.window.StatusBarHeightOwner

const val RANDOM_ICON_URL =
    "https://cnzmrisylnuphrekcdgi.supabase.co/storage/v1/object/public/brain-voyage-bucket/uploads/catalog/random_catalog_image.png"

@Composable
fun OnlineSelectionScreen(
    modifier: Modifier = Modifier,
    selectionViewModel: OnlineSelectionViewModel = hiltViewModel(),
    viewModel: Online2VS2GameViewModel,
    onGameStartScreenNavigate: () -> Unit,
    onBackClick: () -> Unit
) {
    val events by viewModel.event.collectAsStateWithLifecycle(null)
    val catalogLoadingState by selectionViewModel.quizCatalogLoadingState.collectAsStateWithLifecycle()

    val snackbarHostState = remember { SnackbarHostState() }

    var showProgress by rememberSaveable { mutableStateOf(false) }

    BackHandler {
        if (showProgress) viewModel.disconnect()
        onBackClick()
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { padding ->
        when (events) {
            OnlineGameEvent.PlayerConnected -> {
                onGameStartScreenNavigate()
            }

            OnlineGameEvent.Unexpected -> {
                LaunchedEffect(snackbarHostState) {
                    snackbarHostState.showSnackbar("Unexpected error")
                }
            }

            OnlineGameEvent.PlayerInGame -> {
                LaunchedEffect(snackbarHostState) {
                    snackbarHostState.showSnackbar("Player already in game")
                }
            }

            OnlineGameEvent.UserNotFound -> {
                LaunchedEffect(snackbarHostState) {
                    snackbarHostState.showSnackbar("Your account not found")
                }
            }

            OnlineGameEvent.FailurePlayerConnection -> {
                LaunchedEffect(snackbarHostState) {
                    snackbarHostState.showSnackbar("Connection error")
                }
            }

            is OnlineGameEvent.Error -> {
                LaunchedEffect(snackbarHostState) {
                    snackbarHostState.showSnackbar("Network error")
                }
            }

            else -> {}
        }

        Column(
            modifier = modifier
                .fillMaxSize()
                .paint(
                    painter = painterResource(id = R.drawable.background),
                    contentScale = ContentScale.Crop
                )
                .padding(padding)
                .padding(top = if (StatusBarHeightOwner.hasCutout) getStatusBarHeightInDp().dp else 0.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            BackIcon(
                modifier = Modifier.align(Alignment.Start)
            ) {
                if (showProgress) viewModel.disconnect()
                onBackClick()
            }

            if (events is OnlineGameEvent.RoomCreated) {
                SearchEnemyLoading {
                    viewModel.disconnect()
                    onBackClick()
                }
            } else {
                ConnectRoomComponent(
                    catalogLoadingState = catalogLoadingState,
                    showProgress = showProgress,
                    onUpdateSelectedItem = {
                        selectionViewModel.updateSelectedItem(it)
                    },
                    onConnectClick = {
                        showProgress = true
                        viewModel.createOrConnectRoom(catalogLoadingState)
                    }
                )
            }
        }
    }
}




























