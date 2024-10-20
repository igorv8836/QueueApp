package com.example.auth.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.auth.navigation.navigateToMain
import com.example.auth.navigation.navigateToRegister
import com.example.auth.viewmodel.LoginEffect
import com.example.auth.viewmodel.LoginEvent
import com.example.auth.viewmodel.LoginViewModel
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import queueapp.feature.auth.generated.resources.Res
import queueapp.feature.auth.generated.resources.main

@OptIn(KoinExperimentalAPI::class)
@Composable
internal fun LoginScreen(navController: NavController) {
    val viewModel: LoginViewModel = koinViewModel()
    val snackBarHostState = remember { SnackbarHostState() }

    val state by viewModel.container.stateFlow.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.container.sideEffectFlow.collect {
            when (it) {
                is LoginEffect.ShowError -> {
                    snackBarHostState.showSnackbar(it.message)
                }

                is LoginEffect.SuccessLogin -> {
                    snackBarHostState.showSnackbar(it.message)
                    delay(1000)
                    navController.navigateToMain()
                }
            }
        }
    }

    LoginScreen(
        onEvent = viewModel::onEvent,
        navigateToRegister = { navController.navigateToRegister(it) },
        snackBarHostState
    )
}

@Composable
internal fun LoginScreen(
    onEvent: (LoginEvent) -> Unit,
    navigateToRegister: (String?) -> Unit,
    snackBarHostState: SnackbarHostState
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showRecoveryDialog by remember { mutableStateOf(false) }

    if (showRecoveryDialog) {
        RecoveryDialog(
            onConfirm = {
                onEvent(LoginEvent.SendResetCode(it))
                showRecoveryDialog = false
            },
            onDismiss = {
                showRecoveryDialog = false
            }
        )
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it).verticalScroll(rememberScrollState())
        ) {
            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(resource = Res.drawable.main),
                    contentDescription = "App Icon",
                    modifier = Modifier
                        .padding(top = 24.dp)
                        .size(240.dp)
                )


                OutlinedTextField(
                    value = email,
                    onValueChange = { it1 -> email = it1 },
                    label = { Text("Введите почту") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    ),
                    leadingIcon = {
                        Icon(Icons.Default.Email, contentDescription = "email")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp, bottom = 8.dp)
                )

                var passwordVisible by remember { mutableStateOf(false) }
                OutlinedTextField(
                    value = password,
                    onValueChange = { it1 -> password = it1 },
                    label = { Text("Введите пароль") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password
                    ),
                    trailingIcon = {
                        val image =
                            if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                        val description = if (passwordVisible) "Hide password" else "Show password"

                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(imageVector = image, contentDescription = description)
                        }
                    },
                    leadingIcon = {
                        Icon(Icons.Default.Password, contentDescription = "password")
                    },
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth()
                )

                TextButton(
                    onClick = { showRecoveryDialog = true },
                ) {
                    Text("Забыли пароль?")
                }

                Spacer(modifier = Modifier.height(4.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(
                        onClick = { onEvent(LoginEvent.Login(email, password)) },
                        shape = MaterialTheme.shapes.medium,
                        modifier = Modifier
                            .fillMaxWidth(0.5f)
                            .padding(bottom = 8.dp)
                    ) {
                        Text("Войти")
                    }

                    Button(
                        onClick = { navigateToRegister(email) },
                        shape = MaterialTheme.shapes.medium,
                        modifier = Modifier.fillMaxWidth(0.5f)
                    ) {
                        Text("Зарегистрироваться")
                    }
                }

            }
        }

    }
}

@Composable
internal fun RecoveryDialog(
    onConfirm: (String) -> Unit,
    onDismiss: () -> Unit
) {
    var recoveryTextField by remember { mutableStateOf("") }
    AlertDialog(
        title = { Text("Восстановление пароля") },
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = { onConfirm(recoveryTextField) }) {
                Text("Отправить")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Отмена")
            }
        },
        text = {
            Column {
                OutlinedTextField(
                    value = recoveryTextField,
                    onValueChange = { recoveryTextField = it },
                    label = { Text("Введите почту") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    )
}


@Preview
@Composable
internal fun RecoveryDialogPreview() {
    RecoveryDialog({}, {})
}

@Preview
@Composable
internal fun LoginScreenPreview() {
    LoginScreen(
        onEvent = {},
        navigateToRegister = {},
        remember { SnackbarHostState() }
    )
}