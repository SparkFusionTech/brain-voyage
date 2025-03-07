package com.sparkfusion.quiz.brainvoyage.ui.screen.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sparkfusion.quiz.brainvoyage.R
import com.sparkfusion.quiz.brainvoyage.ui.dialog.delete_account.DeleteAccountBottomSheet
import com.sparkfusion.quiz.brainvoyage.ui.dialog.new_pass.NewPasswordBottomSheet
import com.sparkfusion.quiz.brainvoyage.ui.theme.orangeButtonColor
import com.sparkfusion.quiz.brainvoyage.ui.theme.settingsBackgroundDarkColor
import com.sparkfusion.quiz.brainvoyage.ui.theme.settingsBackgroundLightColor
import com.sparkfusion.quiz.brainvoyage.ui.viewmodel.settings.SettingsContract
import com.sparkfusion.quiz.brainvoyage.ui.viewmodel.settings.SettingsViewModel
import com.sparkfusion.quiz.brainvoyage.ui.widget.SFProRoundedText
import com.sparkfusion.quiz.brainvoyage.utils.dp.getStatusBarHeightInDp
import com.sparkfusion.quiz.brainvoyage.window.StatusBarHeightOwner

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    onLogoutClick: () -> Unit
) {
    val dialogsState by viewModel.dialogsState.collectAsStateWithLifecycle()
    val changePasswordState by viewModel.changePasswordState.collectAsStateWithLifecycle()
    val deleteAccountState by viewModel.deleteAccountState.collectAsStateWithLifecycle()
    val clearLoginState by viewModel.clearLoginState.collectAsStateWithLifecycle()
    val readSignInState by viewModel.readSignInState.collectAsStateWithLifecycle()
    val updateSignInAccountState by viewModel.updateSignInState.collectAsStateWithLifecycle()

    val snackbarHostState = remember { SnackbarHostState() }

    val changeNewPasswordDialogVisibility = { v: Boolean ->
        viewModel.handleIntent(
            SettingsContract.SettingsInterface.ChangeNewPasswordDialogVisibility(v)
        )
    }
    val changeDeleteAccountDialogVisibility = { v: Boolean ->
        viewModel.handleIntent(
            SettingsContract.SettingsInterface.ChangeDeleteAccountDialogVisibility(v)
        )
    }

    ShowMessages(
        readSignInState = readSignInState,
        updateSignInAccountState = updateSignInAccountState,
        clearLoginState = clearLoginState,
        changePasswordState = changePasswordState,
        deleteAccountState = deleteAccountState,
        snackbarHostState = snackbarHostState,
        onLogoutClick = onLogoutClick
    )

    Scaffold(
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 12.dp)
            )
        }
    ) { paddings ->
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .background(
                    Brush.linearGradient(
                        listOf(
                            settingsBackgroundLightColor,
                            settingsBackgroundDarkColor
                        )
                    )
                )
                .padding(paddings)
                .padding(top = if (StatusBarHeightOwner.hasCutout) getStatusBarHeightInDp().dp else 0.dp)
        ) {
            item {
                IconButton(
                    modifier = Modifier.padding(start = 8.dp, top = 8.dp),
                    onClick = onBackClick
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.arrow_back_icon),
                        contentDescription = null,
                        tint = Color.White
                    )
                }

                SFProRoundedText(
                    modifier = Modifier
                        .padding(start = 16.dp, top = 24.dp),
                    content = "Account",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Black,
                    color = Color.White
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 2.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    SFProRoundedText(
                        modifier = Modifier,
                        content = "Save account sign in",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )

                    Checkbox(
                        checked = if (readSignInState is SettingsContract.ReadSaveSignInState.Success)
                            (readSignInState as SettingsContract.ReadSaveSignInState.Success).save
                        else true,
                        onCheckedChange = {
                            viewModel.handleIntent(
                                SettingsContract.SettingsInterface.ChangeAccountSignInState(it)
                            )
                        }
                    )
                }

                Spacer(
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(Color.White, RoundedCornerShape(24.dp))
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 6.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    SFProRoundedText(
                        modifier = Modifier,
                        content = "Log out of your account",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )

                    TextButton(
                        modifier = Modifier,
                        onClick = {
                            viewModel.handleIntent(SettingsContract.SettingsInterface.ClearLoginData)
                        }
                    ) {
                        SFProRoundedText(
                            modifier = Modifier.padding(horizontal = 10.dp),
                            content = "Logout",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.Red
                        )
                    }
                }

                Spacer(
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(Color.White, RoundedCornerShape(24.dp))
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .padding(top = 6.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    SFProRoundedText(
                        modifier = Modifier,
                        content = "Change password",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )

                    TextButton(
                        modifier = Modifier,
                        onClick = {
                            changeNewPasswordDialogVisibility(true)
                        }
                    ) {
                        SFProRoundedText(
                            modifier = Modifier.padding(horizontal = 10.dp),
                            content = "Change",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = orangeButtonColor
                        )
                    }
                }

                Spacer(
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(Color.White, RoundedCornerShape(24.dp))
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 6.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    SFProRoundedText(
                        modifier = Modifier,
                        content = "Delete account",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Red
                    )

                    TextButton(
                        modifier = Modifier
                            .background(
                                Color.Red.copy(alpha = 0.2f),
                                RoundedCornerShape(24.dp)
                            )
                            .clip(RoundedCornerShape(24.dp)),
                        onClick = {
                            changeDeleteAccountDialogVisibility(true)
                        }
                    ) {
                        SFProRoundedText(
                            modifier = Modifier.padding(horizontal = 10.dp),
                            content = "Delete",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.Red
                        )
                    }
                }
            }
        }
    }

    NewPasswordBottomSheet(
        show = dialogsState.isNewPasswordDialogVisible,
        onChangePassword = {
            viewModel.handleIntent(SettingsContract.SettingsInterface.ChangePassword(it))
            changeNewPasswordDialogVisibility(false)
        },
        onDismiss = {
            changeNewPasswordDialogVisibility(false)
        }
    )

    DeleteAccountBottomSheet(
        show = dialogsState.isDeleteAccountDialogVisible,
        onDeleteAccount = {
            viewModel.handleIntent(SettingsContract.SettingsInterface.DeleteAccount(it))
        },
        onDismiss = {
            changeDeleteAccountDialogVisibility(false)
        }
    )
}

@Composable
private fun ShowMessages(
    readSignInState: SettingsContract.ReadSaveSignInState,
    updateSignInAccountState: SettingsContract.UpdateSaveSignInState,
    clearLoginState: SettingsContract.ClearLoginState,
    changePasswordState: SettingsContract.ChangePasswordState,
    deleteAccountState: SettingsContract.DeleteAccountState,
    snackbarHostState: SnackbarHostState,
    onLogoutClick: () -> Unit
) {
    LaunchedEffect(Unit) {
        when {
            readSignInState == SettingsContract.ReadSaveSignInState.Error -> {
                snackbarHostState.showSnackbar("Error reading sign-in state")
            }

            updateSignInAccountState == SettingsContract.UpdateSaveSignInState.Error -> {
                snackbarHostState.showSnackbar("Error updating sign-in account")
            }

            clearLoginState is SettingsContract.ClearLoginState.Success -> {
                onLogoutClick()
            }

            clearLoginState is SettingsContract.ClearLoginState.Error -> {
                snackbarHostState.showSnackbar("Error clearing login state")
            }

            changePasswordState == SettingsContract.ChangePasswordState.EmptyValue -> {
                snackbarHostState.showSnackbar("Password value cannot be empty")
            }

            changePasswordState == SettingsContract.ChangePasswordState.Error -> {
                snackbarHostState.showSnackbar("Error changing password")
            }

            changePasswordState == SettingsContract.ChangePasswordState.Success -> {
                snackbarHostState.showSnackbar("Password changed successfully")
            }

            deleteAccountState == SettingsContract.DeleteAccountState.Error -> {
                snackbarHostState.showSnackbar("Error deleting account")
            }

            deleteAccountState == SettingsContract.DeleteAccountState.IncorrectPassword -> {
                snackbarHostState.showSnackbar("Incorrect password provided")
            }

            deleteAccountState == SettingsContract.DeleteAccountState.Success -> {
                onLogoutClick()
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun SettingsScreenPreview() {
    SettingsScreen(
        onBackClick = {},
        onLogoutClick = {}
    )
}

































