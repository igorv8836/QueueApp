package com.example.auth

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.rememberNavController
import com.example.auth.ui.LoginScreen
import com.example.auth.viewmodel.LoginState
import org.jetbrains.compose.ui.tooling.preview.Preview


@Preview
@Composable
internal fun LoginScreenPreview() {
    LoginScreen(
        onEvent = {},
        state = LoginState(),
        navController = rememberNavController(),
        snackBarHostState = remember { SnackbarHostState() }
    )
}