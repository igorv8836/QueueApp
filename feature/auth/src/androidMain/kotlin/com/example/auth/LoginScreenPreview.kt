package com.example.auth

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import com.example.auth.ui.LoginScreen
import org.jetbrains.compose.ui.tooling.preview.Preview


@Preview
@Composable
internal fun LoginScreenPreview() {
    LoginScreen(
        onEvent = {},
        navigateToRegister = {},
        snackBarHostState = SnackbarHostState()
    )
}